package com.example.theapp.ui.checkout.orderplaced.fragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.theapp.R
import com.example.theapp.databinding.FragmentOrderPlacedBinding
import com.example.theapp.ui.myorders.adapter.OrderItemAdapter
import com.example.theapp.ui.myorders.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * TODO :- Correct way of retrieving the last order added in table.
 */
@AndroidEntryPoint
class OrderPlacedFragment : Fragment(R.layout.fragment_order_placed) {

    private val viewModel: OrderViewModel by viewModels()

    private var _binding: FragmentOrderPlacedBinding? = null
    private val binding get() = _binding!!

    private lateinit var orderItemAdapter: OrderItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // To remove the cart icon when on this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrderPlacedBinding.bind(view)

        orderItemAdapter = OrderItemAdapter()

        binding.apply {
            recyclerViewOrderPlaced.apply {
                //setHasFixedSize(true)
                adapter = orderItemAdapter
            }

            buttonContinueShopping.setOnClickListener {
                onContinueShoppingButtonClick()
            }
        }


        viewModel.placedOrder.observe(viewLifecycleOwner) {
            Log.d("OrderPlacedFragment", "$it")
            orderItemAdapter.submitList(it.items)

            binding.apply {
                textViewOrderId.text = "Order #: ${it.id}"
                textViewMarginHeader.text = "Margin: Rs. ${it.margin}"
                textViewDeliveryName.text = it.customer.name
                textViewDeliveryNumber.text = it.customer.phoneNumber
                textViewDeliveryAddressValue.text = "${it.customer.addressLine1} \n${it.customer.addressLine2} \n${it.customer.city}, Pakistan"

                textViewProductChargesValue.text = "${it.productCharges}"
                textViewDeliveryChargesValue.text = "${it.deliveryCharges}"
                textViewOrderTotalValue.text = "${it.orderTotal}"
                textViewMarginValue.text = "${it.margin}"
                textViewCashCollectValue.text = "${it.cashToCollect}"
            }

        }
    }

    // To remove the cart icon on this fragment
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onContinueShoppingButtonClick() {
        val action = OrderPlacedFragmentDirections.actionOrderPlacedFragmentToMarketFragment()
        findNavController().navigate(action)
    }

}