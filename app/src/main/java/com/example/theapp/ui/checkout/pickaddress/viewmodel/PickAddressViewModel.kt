package com.example.theapp.ui.checkout.pickaddress.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.theapp.model.Customer
import com.example.theapp.repository.ICustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PickAddressViewModel @Inject constructor(
    private val customerRepository: ICustomerRepository
) : ViewModel() {

    //val customers: LiveData<List<Customer>> = customerRepository.getCustomers()

    //private val _customers = MutableLiveData<List<Customer>>() // Don't always need backing property, why?
    //val customers: LiveData<List<Customer>>
    //    get() = _customers

    var customers: LiveData<List<Customer>> = customerRepository.getCustomers()

    init {
        //_customers = customerRepository.getCustomers()
        //Log.d("CustomerViewModel", "INIT + ${customers.value}")
    }

    fun addNewCustomer() {
        Log.d("CustomerViewModel", "AddNewCustomer")

        //Log.d("CustomerViewModel", "AddNew + ${mockList}")
        //_customers.value?.add(newCustomer)

        customers = customerRepository.insertCustomer()

        //customers.value?.add(newCustomer)
        //customers.value = mutableListOf(Customer("Josef Stalin", true, null), newCustomer)
    }

    fun onCustomerSelected(position: Int) {
        customers = customerRepository.updateCustomerSelection(position)
    }

}