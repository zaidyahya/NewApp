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
import com.example.theapp.model.CustomerNew
import com.example.theapp.ui.checkout.pickaddress.viewmodel.PickAddressViewModel
import com.example.theapp.ui.mycustomers.fragment.MyCustomersFragmentDirections
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddNewAddressFragment : Fragment(R.layout.fragment_add_new_address) {

    private val viewModel: PickAddressViewModel by activityViewModels()

    private var _binding: FragmentAddNewAddressBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<AddNewAddressFragmentArgs>() // Nullability to be property catered to since this will either be null or populated

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // To remove the cart icon when on this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddNewAddressBinding.bind(view)

        val customer: CustomerNew? = args.customer

        binding.apply {
            buttonSaveAndContinue.setOnClickListener {
                val name = textInputLayoutNameValue.text.toString()
                val phoneNumber = textInputLayoutPhoneNumberValue.text.toString()
                val addressLine1 = textInputLayoutAddressLine1Value.text.toString()
                val addressLine2 = textInputLayoutAddressLine2Value.text.toString()
                val city = textInputLayoutCityValue.text.toString()
                onContinueButtonClick(name, phoneNumber, addressLine1, addressLine2, city)
            }

            customer?.let {
                textInputLayoutNameValue.setText(it.name)
                textInputLayoutPhoneNumberValue.setText(it.phoneNumber)
                textInputLayoutAddressLine1Value.setText(it.addressLine1)
                textInputLayoutAddressLine2Value.setText(it.addressLine2)
                textInputLayoutCityValue.setText(it.city)
                //textInputLayoutName.editText?.setText(it.name)
                //textInputLayoutArea.editText?.setText(it.address?.area)
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

    private fun onContinueButtonClick(name: String, phoneNumber: String,
                                      addressLine1: String, addressLine2: String, city: String
    ) {
        args.customer.let {
            if (it != null) { // If not null meaning Edit is in process
                viewModel.updateCustomer(it.id, name, phoneNumber, addressLine1, addressLine2, city, null)
            } else {
                viewModel.insertCustomer(name, phoneNumber, addressLine1, addressLine2, city, null)
            }
        }

        if(args.sourceFragment == "PickAddressFragment") {
            val action = AddNewAddressFragmentDirections.actionAddNewAddressFragmentToPickAddressFragment()
            findNavController().navigate(action)
        } else if(args.sourceFragment == "MyCustomersFragment") {
            val action = AddNewAddressFragmentDirections.actionAddNewAddressFragmentToMyCustomersFragment()
            findNavController().navigate(action)
        }
    }
}