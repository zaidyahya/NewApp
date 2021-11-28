package com.example.theapp.ui.checkout.ordersummary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.theapp.databinding.DialogBottomModifyAddressBinding
import com.example.theapp.model.Customer
import com.example.theapp.ui.checkout.pickaddress.adapter.AddressItemAdapter
import com.example.theapp.ui.checkout.pickaddress.viewmodel.PickAddressViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ModifyAddressBottomDialog : BottomSheetDialogFragment(), AddressItemAdapter.OnItemClickListener {

    private val viewModel: PickAddressViewModel by activityViewModels()

    private var _binding: DialogBottomModifyAddressBinding? = null
    private val binding get() = _binding!!

    private lateinit var addressItemAdapter: AddressItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogBottomModifyAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addressItemAdapter = AddressItemAdapter(this)

        //val bottomSheet: View? = dialog?.findViewById(R.id.design_bottom_sheet)
        //bottomSheet?.let {
        //    val behavior = BottomSheetBehavior.from(it)
        //    behavior.state = BottomSheetBehavior.STATE_EXPANDED
        //}

        binding.apply {
            recyclerViewPickAddress.apply {
                setHasFixedSize(true)
                adapter = addressItemAdapter
            }
        }

        viewModel.customers.observe(viewLifecycleOwner) {
            addressItemAdapter.submitList(it)
        }
    }

    override fun onItemClick(position: Int) {
        viewModel.onCustomerSelected(position)
    }

    override fun onEditButtonClick(customer: Customer) {
    }

    override fun onDeliverButtonClick(customer: Customer) {
    }
}