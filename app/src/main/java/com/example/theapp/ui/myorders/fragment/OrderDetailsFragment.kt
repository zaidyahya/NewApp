package com.example.theapp.ui.myorders.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.theapp.R
import com.example.theapp.databinding.FragmentOrderDetailsBinding
import com.example.theapp.model.OrderItem
import com.example.theapp.ui.checkout.shoppingcart.adapter.ShoppingCartItemAdapter
import com.example.theapp.ui.checkout.shoppingcart.viewmodel.ShoppingCartViewModel

class OrderDetailsFragment : Fragment(R.layout.fragment_order_details), ShoppingCartItemAdapter.OnItemClickListener {

    private val viewModel: ShoppingCartViewModel by activityViewModels()

    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var shoppingCartAdapter: ShoppingCartItemAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentOrderDetailsBinding.bind(view)

        shoppingCartAdapter = ShoppingCartItemAdapter(this)

        binding.apply {
            recyclerViewOrderDetails.apply {
                setHasFixedSize(true)
                adapter = shoppingCartAdapter
            }
        }

        viewModel.order.observe(viewLifecycleOwner) {
            Log.d("OrderDetailsFragment", "Observed")
            shoppingCartAdapter.setOrderItems(it.items)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onEditButtonClick(orderItem: OrderItem) {

    }


}