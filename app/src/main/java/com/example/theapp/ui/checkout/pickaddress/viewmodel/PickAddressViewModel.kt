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
        val old = customersNew.value?.find { it.isSelected }

        old?.let {
            if(it.id != id) {
                viewModelScope.launch {
                    customerRepository.updateCustomerSelected(old!!.id, false)
                    customerRepository.updateCustomerSelected(id, true) // Transaction?
                }
            }
        } ?: run {
            viewModelScope.launch {
                customerRepository.updateCustomerSelected(id, true)
            }
        }

    }

}