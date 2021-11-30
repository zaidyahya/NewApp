package com.example.theapp.repository

import androidx.lifecycle.LiveData
import com.example.theapp.model.Customer
import com.example.theapp.model.CustomerNew
import kotlinx.coroutines.flow.Flow

interface ICustomerRepository {

    fun getCustomersNew(): Flow<List<CustomerNew>>

    suspend fun insertCustomer(
        name: String,
        phoneNumber: String,
        addressLine1: String,
        addressLine2: String,
        city: String,
        zipCode: String?
    )

    suspend fun updateCustomer(customer: CustomerNew)
    suspend fun updateCustomerSelected(id: String, selected: Boolean)
}