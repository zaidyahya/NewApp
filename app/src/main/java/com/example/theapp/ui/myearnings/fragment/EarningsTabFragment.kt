package com.example.theapp.ui.myearnings.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.theapp.R
import com.example.theapp.databinding.FragmentTabEarningsBinding
import com.example.theapp.ui.myearnings.adapter.EarningItemAdapter
import com.example.theapp.ui.myorders.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EarningsTabFragment : Fragment(R.layout.fragment_tab_earnings) {

    private val viewModel: OrderViewModel by viewModels()

    private var _binding: FragmentTabEarningsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTabEarningsBinding.bind(view)

        viewModel.getCompletedOrders()

        val earningItemAdapter = EarningItemAdapter()

        binding.apply {
            recyclerViewEarningsLog.apply {
                setHasFixedSize(true)
                adapter = earningItemAdapter
            }
        }

        viewModel.completedOrders.observe(viewLifecycleOwner) {
            earningItemAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}