package com.example.theapp.ui.store.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theapp.R
import com.example.theapp.databinding.FragmentStoreBinding

class StoreFragment : Fragment(R.layout.fragment_store) {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentStoreBinding.bind(view)

        binding.apply {
            textViewMyOrders.setOnClickListener {
                val action = StoreFragmentDirections.actionStoreFragmentToMyOrdersFragment()
                findNavController().navigate(action)
            }
            textViewMyEarnings.setOnClickListener{
                val action = StoreFragmentDirections.actionStoreFragmentToMyEarningsFragment()
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}