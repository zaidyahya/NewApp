package com.example.theapp.ui.product.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.example.theapp.R
import com.example.theapp.databinding.DialogBottomAddToCartBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

class AddToCartBottomDialog(
    private val productVariants: List<String>,
    private val listener: OnItemClickListener
) : BottomSheetDialogFragment() {

    private var _binding: DialogBottomAddToCartBinding? = null
    private val binding get() = _binding!!

    private val sizes = listOf("S", "M", "L", "XL")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogBottomAddToCartBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chipGroup = binding.chipGroupSizes
        for(i in productVariants.indices) {
            val newChip: Chip = createChip(productVariants[i])
            if(i == 0) {
                newChip.isChecked = true
            }
            chipGroup.addView(newChip)
        }

        binding.apply {
            textInputLayoutQuantityValue.setText("0")

            buttonSubtract.setOnClickListener{
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

            textInputLayoutQuantityValue.doOnTextChanged { text, start, before, count ->
                textInputLayoutQuantity.isErrorEnabled = false
            }


            buttonContinue.setOnClickListener {
                val quantity: Int = textInputLayoutQuantityValue.text.toString().toInt()
                if(quantity == 0) {
                    textInputLayoutQuantity.error = "Please add a quantity"
                } else {
                    val selectedChip = view.findViewById<Chip>(chipGroup.checkedChipId)
                    listener.onContinueButtonClick("${selectedChip.text}", quantity)
                    dismiss()
                }
            }
        }
    }

    private fun createChip(text: String) : Chip {
        val newChip = Chip(context, null, R.attr.styleChip) // https://stackoverflow.com/questions/52401709/apply-style-to-materialbutton-programmatically
        newChip.text = text
        return newChip
    }

    interface OnItemClickListener {
        fun onContinueButtonClick(size: String, quantity: Int)
    }
}