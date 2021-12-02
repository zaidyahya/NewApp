package com.example.theapp.ui.checkout.shoppingcart.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.theapp.R
import com.example.theapp.databinding.DialogBottomModifyProductBinding
import com.example.theapp.model.ShoppingCartItemWithDetails
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ModifyProductBottomDialog(
    //private val orderItem: OrderItem,
    private val cartItem: ShoppingCartItemWithDetails,
    private val productVariants: List<String>,
    private val listener: OnItemClickListener
) : BottomSheetDialogFragment() {

    private var _binding: DialogBottomModifyProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogBottomModifyProductBinding.inflate(inflater, container, false)

        binding.apply {
            val adapter = ArrayAdapter(requireContext(), R.layout.item_size_menu, productVariants)
            val autoCompleteTextView = textInputLayoutSize.editText as? AutoCompleteTextView
            autoCompleteTextView?.setAdapter(adapter)
            autoCompleteTextView?.setText(cartItem.sizeAbbreviation, false)

            imageViewProduct.setImageResource(R.drawable.kurta_1)
            textViewName.text = cartItem.productName
            textViewTotalPriceValue.text = "Rs. ${getTotalPrice()}"

            buttonRemove.setOnClickListener{
                listener.onRemoveButtonClick(cartItem.id)
                dismiss()
            }

            buttonClose.setOnClickListener {
                dismiss()
            }

            textInputLayoutQuantityValue.setText("${cartItem.quantity}")
            buttonSubtract.setOnClickListener {
                var textInputValue = textInputLayoutQuantityValue.text.toString()
                var value: Int = textInputValue.toInt()
                if(value != 0) {
                    value -= 1
                    textInputLayoutQuantityValue.setText(value.toString())
                }
            }

            buttonAdd.setOnClickListener {
                var textInputValue = textInputLayoutQuantityValue.text.toString()
                var value: Int = textInputValue.toInt()
                value += 1
                textInputLayoutQuantityValue.setText(value.toString())
            }

            buttonContinue.setOnClickListener {
                val quantity: Int = textInputLayoutQuantityValue.text.toString().toInt()
                if(quantity == 0) {
                    textInputLayoutQuantity.error = "Please add a quantity"
                } else {
                    val selectedSize: String = autoCompleteTextView?.text.toString()
                    listener.onContinueButtonClick(selectedSize, quantity)
                    dismiss()
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getTotalPrice(): Int {
        return cartItem.productPrice.toInt() * cartItem.quantity.toInt()
    }

    interface OnItemClickListener {
        fun onRemoveButtonClick(id: String)
        fun onContinueButtonClick(size: String, quantity: Int)
    }
}