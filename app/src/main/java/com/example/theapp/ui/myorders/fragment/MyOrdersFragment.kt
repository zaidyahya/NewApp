package com.example.theapp.ui.myorders.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.theapp.R
import com.example.theapp.databinding.FragmentMyOrdersBinding
import com.example.theapp.ui.myorders.adapter.OrderSummarizedAdapter
import com.example.theapp.ui.myorders.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOrdersFragment : Fragment(R.layout.fragment_my_orders), OrderSummarizedAdapter.OnItemClickListener {

    private val viewModel: OrderViewModel by viewModels()

    private var _binding: FragmentMyOrdersBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMyOrdersBinding.bind(view)

        val orderItemAdapter = OrderSummarizedAdapter(this)

        binding.apply {
            recyclerViewOrders.apply {
                //setHasFixedSize(true)
                adapter = orderItemAdapter
            }

            chipGroupTypes.setOnCheckedChangeListener { group, checkedId ->
                onChipSelect(checkedId)
            }
        }

        viewModel.orders.observe(viewLifecycleOwner) {
            Log.d("ORDERSCHANGED", "${it.size}")
            orderItemAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(id: String) {
        val action = MyOrdersFragmentDirections.actionMyOrdersFragmentToOrderDetailsFragment(id)
        findNavController().navigate(action)
    }

    private fun onChipSelect(id: Int) {
        Log.d("ChipSelect","ChipID :- $id")
        when(id) {
            // 1 -> All, 2 -> Completed, 3 -> In Progress, 4 -> Cancelled
            1 -> viewModel.onChipSelected("All")
            2 -> viewModel.onChipSelected("Completed")
            3 -> viewModel.onChipSelected("In Progress")
            4 -> viewModel.onChipSelected("Cancelled")
        }
    }
}