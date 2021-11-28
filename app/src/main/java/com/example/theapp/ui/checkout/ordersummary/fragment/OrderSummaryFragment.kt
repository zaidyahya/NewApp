package com.example.theapp.ui.checkout.ordersummary.fragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.theapp.R
import com.example.theapp.databinding.FragmentOrderSummaryBinding
import com.example.theapp.model.OrderItem
import com.example.theapp.ui.checkout.shoppingcart.adapter.ShoppingCartItemAdapter
import com.example.theapp.ui.checkout.shoppingcart.fragment.ModifyProductBottomDialog
import com.example.theapp.ui.checkout.shoppingcart.viewmodel.ShoppingCartViewModel

class OrderSummaryFragment : Fragment(R.layout.fragment_order_summary), ShoppingCartItemAdapter.OnItemClickListener,
ModifyProductBottomDialog.OnItemClickListener {

    private val viewModel: ShoppingCartViewModel by activityViewModels()

    private var _binding: FragmentOrderSummaryBinding? = null
    private val binding get() = _binding!!

    private lateinit var shoppingCartAdapter: ShoppingCartItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // To remove the cart icon when on this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrderSummaryBinding.bind(view)

        shoppingCartAdapter = ShoppingCartItemAdapter(this)

        binding.apply {
            recyclerViewShoppingCart.apply {
                setHasFixedSize(true)
                adapter = shoppingCartAdapter
            }

            textViewCustomerName.text = viewModel.order.value?.customer?.name
            binding.imageButtonEditAddress.setOnClickListener {
                activity?.let { ModifyAddressBottomDialog().show(it.supportFragmentManager, "ModifyAddressDialog") }
            }

            binding.buttonPlaceOrder.setOnClickListener {
                onPlaceOrderButtonClick()
            }
        }

        viewModel.order.observe(viewLifecycleOwner) {
            Log.d("OrderSummaryFragment", "Observed")
            shoppingCartAdapter.setOrderItems(it.items)
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

    override fun onEditButtonClick(orderItem: OrderItem) {
        //activity?.let { ModifyProductBottomDialog(orderItem, this).show(it.supportFragmentManager, "ModifyProductBottomDialog")}
    }

    override fun onRemoveButtonClick(id: String) {
        viewModel.removeOrderItem(id)
    }

    override fun onContinueButtonClick(id: String, size: Int) {
        //viewModel.updateOrderItem(id, size)
    }

    private fun onPlaceOrderButtonClick() {
        val action = OrderSummaryFragmentDirections.actionOrderSummaryFragmentToOrderPlacedFragment()
        findNavController().navigate(action)
    }
}