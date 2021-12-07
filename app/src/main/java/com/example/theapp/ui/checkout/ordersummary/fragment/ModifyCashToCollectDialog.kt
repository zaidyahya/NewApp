package com.example.theapp.ui.checkout.ordersummary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.example.theapp.databinding.DialogBottomModifyCashToCollectBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * TODO :- New CashCollect error-checking
 */
class ModifyCashToCollectDialog(
    private val newOrderTotal: Int,
    private val listener: OnItemClickListener
) : BottomSheetDialogFragment() {

    private var _binding: DialogBottomModifyCashToCollectBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogBottomModifyCashToCollectBinding.inflate(inflater, container, false)
        this.isCancelable = false // To prevent dialog form hiding on clicks on side

        binding.apply {
            textViewNewOrderTotalValue.text = "Rs. $newOrderTotal"
            textInputLayoutCashCollect.hint = "Order Total (Rs. $newOrderTotal) + Your Margin"

            textInputLayoutCashCollectValue.doOnTextChanged { text, start, before, count ->
                textInputLayoutCashCollect.isErrorEnabled = false
            }

            buttonUpdate.setOnClickListener {
                val textCashCollect = textInputLayoutCashCollectValue.text.toString()

                if(textCashCollect == "") {
                    textInputLayoutCashCollect.isErrorEnabled = true
                    textInputLayoutCashCollect.error = "Please enter a value"
                } else {
                    val margin = calculateMargin(textCashCollect)
                    if(margin <= 0) {
                        textInputLayoutCashCollect.isErrorEnabled = true
                        textInputLayoutCashCollect.error = "Amount is too low"
                    } else {
                        val cashToCollect = textCashCollect.toInt()
                        listener.onUpdateButtonClick(cashToCollect)
                        dismiss()
                    }
                }

            }

            buttonClose.setOnClickListener {
                textInputLayoutCashCollect.isErrorEnabled = true
                textInputLayoutCashCollect.error = "Please enter a value"
                //dismiss()
            }
        }

        return binding.root
    }

    interface OnItemClickListener {
        fun onUpdateButtonClick(value: Int)
    }

    private fun calculateMargin(valueEntered: CharSequence) : Int {
        if(valueEntered.isNotEmpty()) {
            return "$valueEntered".toInt() - newOrderTotal
        }
        return 0
    }
}