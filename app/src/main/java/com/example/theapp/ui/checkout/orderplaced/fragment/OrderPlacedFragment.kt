package com.example.theapp.ui.checkout.orderplaced.fragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.theapp.R
import com.example.theapp.databinding.FragmentOrderPlacedBinding
import com.example.theapp.model.OrderItem
import com.example.theapp.ui.checkout.shoppingcart.adapter.ShoppingCartItemAdapter
import com.example.theapp.ui.checkout.shoppingcart.viewmodel.ShoppingCartViewModel

class OrderPlacedFragment : Fragment(R.layout.fragment_order_placed), ShoppingCartItemAdapter.OnItemClickListener {

    private val viewModel: ShoppingCartViewModel by activityViewModels()

    private var _binding: FragmentOrderPlacedBinding? = null
    private val binding get() = _binding!!

    private lateinit var shoppingCartAdapter: ShoppingCartItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // To remove the cart icon when on this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrderPlacedBinding.bind(view)

        shoppingCartAdapter = ShoppingCartItemAdapter(this)

        binding.apply {
            recyclerViewOrderPlaced.apply {
                setHasFixedSize(true)
                adapter = shoppingCartAdapter
            }

            buttonContinueShopping.setOnClickListener {
                onContinueShoppingButtonClick()
            }
        }

        viewModel.order.observe(viewLifecycleOwner) {
            Log.d("OrderPlacedFragment", "Observed")
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

    }

    private fun onContinueShoppingButtonClick() {
        val action = OrderPlacedFragmentDirections.actionOrderPlacedFragmentToMarketFragment()
        findNavController().navigate(action)
    }

}