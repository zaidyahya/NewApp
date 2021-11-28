package com.example.theapp.repository

import androidx.lifecycle.LiveData
import com.example.theapp.model.Customer

interface ICustomerRepository {

    fun getCustomers(): LiveData<List<Customer>>

    fun insertCustomer(): LiveData<List<Customer>>

    fun updateCustomerSelection(position: Int): LiveData<List<Customer>>
}