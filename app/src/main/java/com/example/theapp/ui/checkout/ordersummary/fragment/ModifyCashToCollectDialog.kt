package com.example.theapp.ui.checkout.ordersummary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.theapp.databinding.DialogBottomModifyCashToCollectBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ModifyCashToCollectDialog(
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

        binding.apply {
            buttonUpdate.setOnClickListener {
                val cashToCollect = textInputLayoutCashCollectValue.text.toString().toInt()
                listener.onUpdateButtonClick(cashToCollect)
                dismiss()
            }
        }

        return binding.root
    }

    interface OnItemClickListener {
        fun onUpdateButtonClick(value: Int)
    }
}