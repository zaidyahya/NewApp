package com.example.theapp.ui.myorders.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.theapp.R
import com.example.theapp.databinding.FragmentOrderDetailsBinding
import com.example.theapp.ui.myorders.adapter.OrderItemAdapter
import com.example.theapp.ui.myorders.viewmodel.OrderViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailsFragment : Fragment(R.layout.fragment_order_details) {

    private val viewModel: OrderViewModel by viewModels()

    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<OrderDetailsFragmentArgs>()

    private lateinit var orderItemAdapter: OrderItemAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentOrderDetailsBinding.bind(view)
        val orderId: String = args.orderId
        viewModel.getOrderById(orderId)

        orderItemAdapter = OrderItemAdapter()

        binding.apply {
            recyclerViewOrderDetails.apply {
                //setHasFixedSize(true)
                adapter = orderItemAdapter
            }
        }

        viewModel.order.observe(viewLifecycleOwner) {
            orderItemAdapter.submitList(it.items)

            binding.apply {
                textViewOrderNumber.text = "Order #: ${it.id}"
                textViewCustomerNameValue.text = it.customer.name

                textViewStatusValue.text = it.status
                when(it.status) { //https://mui.com/customization/palette/
                    "Completed" -> textViewStatusValue.setTextColor(Color.parseColor("#388e3c"))
                    "In Progress" -> {
                        textViewStatusValue.setTextColor(Color.parseColor("#f57c00"))
                        buttonCancel.visibility = View.VISIBLE
                    }
                    "Cancelled" -> {
                        textViewStatusValue.setTextColor(Color.parseColor("#d32f2f"))
                        buttonCancel.visibility = View.GONE // Because when the order is cancelled from in progress, Button remains visible.
                    }
                }

                textViewProductChargesValue.text = "Rs. ${it.productCharges}"
                textViewDeliveryChargesValue.text = "Rs. ${it.deliveryCharges}"
                textViewOrderTotalValue.text = "Rs. ${it.orderTotal}"
                textViewMarginValue.text = "Rs. ${it.margin}"
                textViewCashCollectValue.text = "Rs. ${it.cashToCollect}"

                textViewName.text = it.customer.name
                textViewAddress.text = "${it.customer.addressLine1} \n${it.customer.addressLine2} \n${it.customer.city}, Pakistan"
                textViewPhoneNumber.text = it.customer.phoneNumber

                buttonCancel.setOnClickListener {
                    onCancelButtonClick(orderId)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * TODO :- Confirmation Dialog
     */
    private fun onCancelButtonClick(id: String) {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle("Are you sure you want to cancel this order?")
                .setMessage("Please note that this action cannot be undone.")
                .setNegativeButton("No") { dialog, which ->

                }
                .setPositiveButton("Yes") { dialog, which ->
                    viewModel.onCancelButtonClick(id)
                }
                .show()
        }
    }


}