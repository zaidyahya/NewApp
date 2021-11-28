package com.example.theapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.theapp.R
import com.example.theapp.model.Customer
import com.example.theapp.model.Order
import com.example.theapp.model.OrderItem
import com.example.theapp.model.Product
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Need to figure out what the right way is to edit, delete things + how/which methods need to return a value and which don't.
 * Returning depends on Flow/Co-routines/RxJava as to how they work. Will they auto update LiveData values in ViewModel and thus UI?
 * Correct way to edit/delete depends on SQLDelight because it will actually happen in the DB
 */
@Singleton
class OrderRepository @Inject constructor(): IOrderRepository {

    val itemOne = OrderItem(UUID.randomUUID().toString(), Product("Product 3", R.drawable.green_big, ""),"S")
    val itemTwo = OrderItem(UUID.randomUUID().toString(), Product("Product 1", R.drawable.kurta_1, ""), "XXL")

    private var mockOrder = MutableLiveData(
//        Order(listOf(itemOne, itemTwo))
        Order(listOf())
    )

    override fun getOrder(): LiveData<Order> {
        return mockOrder
    }

    override fun addOrderItem(product: Product, size: String): LiveData<Order> {
        val newList = mockOrder.value!!.items.toMutableList()
        val newOrderItem = OrderItem(UUID.randomUUID().toString(), product, size)
        newList.add(newOrderItem)

        val newOrder = Order(newList)

        mockOrder.value = newOrder

        return mockOrder
    }

    override fun deleteOrderItem(id: String): LiveData<Order> {
        val newList = mockOrder.value!!.items.toMutableList()

        val itemToRemove = newList.find{it.id == id}
        newList.remove(itemToRemove)

        val newOrder = Order(newList)

        mockOrder.value = newOrder
        return mockOrder
    }

    override fun updateOrderItem(id: String, size: String): LiveData<Order> {
        val newList = mockOrder.value!!.items.toMutableList()
        var itemToUpdate = newList.find{it.id == id}
        //itemToUpdate.size = size // Cannot simply reassign it as properties are val. Don't think they should be var

        val updatedItem = OrderItem(itemToUpdate!!.id, itemToUpdate.product, size)
        newList.remove(itemToUpdate)
        newList.add(updatedItem)

        val newOrder = Order(newList)
        mockOrder.value = newOrder
        return mockOrder
    }

    override fun addCustomer(customer: Customer): LiveData<Order> {
        val newList = mockOrder.value!!.items.toMutableList()
        val newOrder = Order(newList, customer)
        mockOrder.value = newOrder
        return mockOrder
    }
}