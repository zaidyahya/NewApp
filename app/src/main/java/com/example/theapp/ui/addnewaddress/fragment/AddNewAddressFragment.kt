package com.example.theapp.ui.addnewaddress.fragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.theapp.R
import com.example.theapp.databinding.FragmentAddNewAddressBinding
import com.example.theapp.model.Customer
import com.example.theapp.ui.checkout.pickaddress.viewmodel.PickAddressViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddNewAddressFragment : Fragment(R.layout.fragment_add_new_address) {

    private val viewModel: PickAddressViewModel by activityViewModels()

    private var _binding: FragmentAddNewAddressBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<AddNewAddressFragmentArgs>()
    //private lateinit var customer: Customer // Nullability to be property catered to since this will either be null or populated

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // To remove the cart icon when on this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddNewAddressBinding.bind(view)

        val customer: Customer? = args.customer

        binding.apply {
            buttonSaveAndContinue.setOnClickListener { onContinueButtonClick() }

            customer?.let {
                binding.textInputLayoutName.editText?.setText(it.name)
                binding.textInputLayoutArea.editText?.setText(it.address?.area)
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

    private fun onContinueButtonClick() {
        Log.d("AddNewAddress", "ContinueButton")
        viewModel.addNewCustomer()
        val action = AddNewAddressFragmentDirections.actionAddNewAddressFragmentToPickAddressFragment()
        findNavController().navigate(action)
    }
}