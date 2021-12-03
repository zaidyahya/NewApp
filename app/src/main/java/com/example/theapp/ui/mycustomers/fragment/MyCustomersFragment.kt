package com.example.theapp.ui.mycustomers.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.theapp.R
import com.example.theapp.databinding.FragmentPickAddressBinding
import com.example.theapp.model.CustomerNew
import com.example.theapp.ui.checkout.pickaddress.viewmodel.PickAddressViewModel
import com.example.theapp.ui.mycustomers.adapter.CustomerItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCustomersFragment: Fragment(R.layout.fragment_pick_address), CustomerItemAdapter.OnItemClickListener {

    private val viewModel: PickAddressViewModel by viewModels()

    private var _binding: FragmentPickAddressBinding? = null
    private val binding get() = _binding!!

    private lateinit var customerItemAdapter: CustomerItemAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customerItemAdapter = CustomerItemAdapter(this)
        _binding = FragmentPickAddressBinding.bind(view)

        binding.apply {
            recyclerViewPickAddress.apply {
                setHasFixedSize(true)
                adapter = customerItemAdapter
            }

            buttonAddNew.setOnClickListener {
                val action = MyCustomersFragmentDirections.actionMyCustomersFragmentToAddNewAddressFragment(null,"MyCustomersFragment")
                findNavController().navigate(action)
            }
        }

        viewModel.customersNew.observe(viewLifecycleOwner) {
            customerItemAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onEditButtonClick(customer: CustomerNew) {
        val action = MyCustomersFragmentDirections.actionMyCustomersFragmentToAddNewAddressFragment(customer,"MyCustomersFragment")
        findNavController().navigate(action)
    }

}