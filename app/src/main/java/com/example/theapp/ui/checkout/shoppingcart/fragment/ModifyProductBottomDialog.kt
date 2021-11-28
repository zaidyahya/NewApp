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

        //val items = listOf("S", "M", "L", "XXL")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_size_menu, productVariants)

        val autoCompleteTextView = binding.textInputLayoutSize.editText as? AutoCompleteTextView
        autoCompleteTextView?.setAdapter(adapter)
        //autoCompleteTextView?.setText("S",false)
        autoCompleteTextView?.setText(cartItem.sizeAbbreviation, false)

        binding.imageViewProduct.setImageResource(R.drawable.kurta_1)
        //orderItem.product.image?.let {
        //    binding.imageViewProduct.setImageResource(it)
        //}
        binding.textViewName.text = cartItem.productName

        binding.textViewTotalPriceValue.text = "Rs. ${getTotalPrice()}"

        binding.buttonRemove.setOnClickListener {
            listener.onRemoveButtonClick(cartItem.id)
            dismiss()
        }

        binding.buttonClose.setOnClickListener{
            dismiss()
        }

        binding.buttonContinue.setOnClickListener {
            val selectedSize: String = autoCompleteTextView?.text.toString()
            listener.onContinueButtonClick(selectedSize, 10)
            dismiss()
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