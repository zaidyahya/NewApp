package com.example.theapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.theapp.model.Customer
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomerRepository @Inject constructor() : ICustomerRepository {

    private var mockList = MutableLiveData(listOf(          // Cos you can't have LiveData initialization? Abstract class?
        Customer(UUID.randomUUID().toString(), "Josef Stalin", true, null), Customer(UUID.randomUUID().toString(), "Josef Stalin", false, null), Customer(UUID.randomUUID().toString(), "Josef Stalin", false, null),
        Customer(UUID.randomUUID().toString(), "Josef Stalin", false, null), Customer(UUID.randomUUID().toString(), "Josef Stalin", false, null), Customer(UUID.randomUUID().toString(), "Josef Stalin", false, null)
    ))
    //private val mockList = mutableListOf(Customer("Josef Stalin", true, null))

    override fun getCustomers(): LiveData<List<Customer>> {
        Log.d("CustomerRepository", "GetCustomers")
        return mockList
    }

    override fun insertCustomer(): LiveData<List<Customer>>  {
        Log.d("CustomerRepository", "InsertCustomer")
        val newCustomer = Customer(UUID.randomUUID().toString(), "Private Ryan", false, null)

        //mockList = MutableLiveData(mutableListOf(Customer("Josef Stalin", true, null), newCustomer))
        //mockList.value?.add(newCustomer)

        val newList = mockList.value?.toMutableList()
        newList?.add(newCustomer)

        //mockList.value = listOf(Customer(1,"Josef Stalin", true, null), newCustomer)
        mockList.value = newList
        return mockList
    }

    override fun updateCustomerSelection(position: Int): LiveData<List<Customer>> {
        val newList = mockList.value?.toMutableList()
        for(i in newList!!.indices) {
            val oldCustomer = newList[i]
            newList[i] = Customer(oldCustomer.id, oldCustomer.name, i == position, oldCustomer.address)
        }
        mockList.value = newList
        return  mockList
    }
}