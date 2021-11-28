package com.example.theapp.ui.myearnings.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.theapp.R
import com.example.theapp.databinding.FragmentTabPaymentHistoryBinding
import com.example.theapp.ui.myearnings.adapter.PaymentHistoryItemAdapter

class PaymentHistoryTabFragment : Fragment(R.layout.fragment_tab_payment_history) {

    private var _binding: FragmentTabPaymentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTabPaymentHistoryBinding.bind(view)

        binding.apply {
            recyclerViewPaymentHistoryLog.apply {
                setHasFixedSize(true)
                adapter = PaymentHistoryItemAdapter()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}