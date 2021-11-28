package com.example.theapp.repository

import androidx.lifecycle.LiveData
import com.example.theapp.model.Customer
import com.example.theapp.model.Order
import com.example.theapp.model.Product

interface IOrderRepository {

    fun getOrder(): LiveData<Order>
    fun addOrderItem(product: Product, size: String): LiveData<Order>
    fun deleteOrderItem(id: String): LiveData<Order>
    fun updateOrderItem(id: String, size: String): LiveData<Order>
    fun addCustomer(customer: Customer): LiveData<Order>

}