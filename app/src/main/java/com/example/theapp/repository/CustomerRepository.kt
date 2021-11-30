package com.example.theapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.theapp.localdatasource.CustomerLocalDataSource
import com.example.theapp.model.Customer
import com.example.theapp.model.CustomerNew
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomerRepository @Inject constructor(
    private val customerLocalDataSource: CustomerLocalDataSource
) : ICustomerRepository {

    private var mockList = MutableLiveData(listOf(          // Cos you can't have LiveData initialization? Abstract class?
        Customer(UUID.randomUUID().toString(), "Josef Stalin", true, null), Customer(UUID.randomUUID().toString(), "Josef Stalin", false, null), Customer(UUID.randomUUID().toString(), "Josef Stalin", false, null),
        Customer(UUID.randomUUID().toString(), "Josef Stalin", false, null), Customer(UUID.randomUUID().toString(), "Josef Stalin", false, null), Customer(UUID.randomUUID().toString(), "Josef Stalin", false, null)
    ))
    //private val mockList = mutableListOf(Customer("Josef Stalin", true, null))

    override fun getCustomersNew(): Flow<List<CustomerNew>> {
        return customerLocalDataSource.getCustomers()
    }

    override suspend fun insertCustomer(
        name: String,
        phoneNumber: String,
        addressLine1: String,
        addressLine2: String,
        city: String,
        zipCode: String?
    ) {
        customerLocalDataSource.insertCustomer(
            CustomerNew(
                id = UUID.randomUUID().toString(),
                name = name,
                phoneNumber = phoneNumber,
                addressLine1 = addressLine1,
                addressLine2 = addressLine2,
                city = city,
                zipCode = zipCode
            )
        )
    }

    override suspend fun updateCustomer(customer: CustomerNew) {
        customerLocalDataSource.updateCustomer(customer)
    }

    override suspend fun updateCustomerSelected(id: String, selected: Boolean) {
        customerLocalDataSource.updateCustomerSelected(id, selected)
    }
}