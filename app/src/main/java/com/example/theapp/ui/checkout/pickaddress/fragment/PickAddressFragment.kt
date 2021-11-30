package com.example.theapp.ui.checkout.pickaddress.fragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.theapp.R
import com.example.theapp.databinding.FragmentPickAddressBinding
import com.example.theapp.model.Customer
import com.example.theapp.model.CustomerNew
import com.example.theapp.ui.checkout.pickaddress.adapter.AddressItemAdapter
import com.example.theapp.ui.checkout.pickaddress.adapter.AddressItemAdapterNew
import com.example.theapp.ui.checkout.pickaddress.viewmodel.PickAddressViewModel
import com.example.theapp.ui.checkout.shoppingcart.viewmodel.ShoppingCartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PickAddressFragment : Fragment(R.layout.fragment_pick_address), AddressItemAdapterNew.OnItemClickListener {

    private val cartViewModel: ShoppingCartViewModel by activityViewModels()
    private val viewModel: PickAddressViewModel by activityViewModels()

    private var _binding: FragmentPickAddressBinding? = null
    private val binding get() = _binding!!

    //private lateinit var addressItemAdapter: AddressItemAdapter
    private lateinit var addressItemAdapterNew: AddressItemAdapterNew


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // To remove the cart icon when on this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val addressItems = viewModel.customers.value
        //addressItemAdapter = AddressItemAdapter(addressItems!!, this)

        //addressItemAdapter = AddressItemAdapter(this)
        addressItemAdapterNew = AddressItemAdapterNew(this)

        _binding = FragmentPickAddressBinding.bind(view)

        binding.apply {
            recyclerViewPickAddress.apply {
                setHasFixedSize(true)
                adapter = addressItemAdapterNew
            }

            buttonAddNew.setOnClickListener {
                val action = PickAddressFragmentDirections.actionPickAddressFragmentToAddNewAddressFragment(null)
                findNavController().navigate(action)
            }
        }

        viewModel.customersNew.observe(viewLifecycleOwner) {
            Log.d("CustomerObservers", "$it")
            addressItemAdapterNew.submitList(it)
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

    override fun onItemClick(id: String, position: Int) {
        viewModel.onItemClick(id, position)
    }

    override fun onEditButtonClick(customer: CustomerNew) {
        val action = PickAddressFragmentDirections.actionPickAddressFragmentToAddNewAddressFragment(customer)
        findNavController().navigate(action)
    }

    override fun onDeliverButtonClick(customer: CustomerNew) {
        val action = PickAddressFragmentDirections.actionPickAddressFragmentToOrderSummaryFragment(customer)
        findNavController().navigate(action)
    }
}