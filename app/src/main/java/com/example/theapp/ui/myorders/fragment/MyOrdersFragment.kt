package com.example.theapp.ui.myorders.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theapp.R
import com.example.theapp.databinding.FragmentMyOrdersBinding
import com.example.theapp.ui.myorders.adapter.OrderItemAdapter

class MyOrdersFragment : Fragment(R.layout.fragment_my_orders), OrderItemAdapter.OnItemClickListener {

    private var _binding: FragmentMyOrdersBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMyOrdersBinding.bind(view)

        val orderItemAdapter = OrderItemAdapter(this)

        binding.apply {
            recyclerViewOrders.apply {
                setHasFixedSize(true)
                adapter = orderItemAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick() {
        val action = MyOrdersFragmentDirections.actionMyOrdersFragmentToOrderDetailsFragment()
        findNavController().navigate(action)
    }
}