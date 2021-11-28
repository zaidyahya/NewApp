package com.example.theapp.ui.checkout.shoppingcart.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theapp.R
import com.example.theapp.databinding.FragmentShoppingCartEmptyBinding

class ShoppingCartEmptyFragment : Fragment(R.layout.fragment_shopping_cart_empty) {

    private var _binding: FragmentShoppingCartEmptyBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentShoppingCartEmptyBinding.bind(view)

        binding.apply {
            buttonBrowse.setOnClickListener {
                val action = ShoppingCartEmptyFragmentDirections.actionShoppingCartEmptyFragmentToMarketFragment()
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}