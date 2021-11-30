package com.example.theapp.ui.checkout.pickaddress.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.theapp.model.Customer
import com.example.theapp.model.CustomerNew
import com.example.theapp.repository.ICustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickAddressViewModel @Inject constructor(
    private val customerRepository: ICustomerRepository
) : ViewModel() {

    //private val _customers = MutableLiveData<List<Customer>>() // Don't always need backing property, why?
    //val customers: LiveData<List<Customer>>
    //    get() = _customers

    //var customers: LiveData<List<Customer>> = customerRepository.getCustomers()

    val customersNew: LiveData<List<CustomerNew>> = customerRepository.getCustomersNew().asLiveData()

    fun insertCustomer(
        name: String,
        phoneNumber: String,
        addressLine1: String,
        addressLine2: String,
        city: String,
        zipCode: String?
    ) {
        viewModelScope.launch {
            customerRepository.insertCustomer(name, phoneNumber, addressLine1, addressLine2, city, zipCode)
        }
    }

    fun updateCustomer(
        id: String,
        name: String,
        phoneNumber: String,
        addressLine1: String,
        addressLine2: String,
        city: String,
        zipCode: String?
    ) {
        viewModelScope.launch {
            customerRepository.updateCustomer(
                CustomerNew(id = id, name = name, phoneNumber = phoneNumber, addressLine1 = addressLine1, addressLine2 = addressLine2, city = city, zipCode = zipCode)
            )
        }
    }

    fun onItemClick(id: String, position: Int) {
        /**
         *  TODO :- Catering to required for the first Customer added. Will likely crash
         */
        val old = customersNew.value?.find { it.isSelected }

        if(old?.id != id) {
            viewModelScope.launch {
                customerRepository.updateCustomerSelected(old!!.id, false)
                customerRepository.updateCustomerSelected(id, true) // Transaction?
            }
        }
    }

}