package com.example.theapp.localdatasource

import com.example.theapp.SelectAllOrders
import com.example.theapp.SelectFullOrderById
import com.example.theapp.database.OrderDao
import com.example.theapp.database.ShoppingCartDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderLocalDataSource @Inject constructor(
    private val orderDao: OrderDao
) {

    fun getOrders(): Flow<List<SelectAllOrders>> {
        return orderDao.getOrders()
    }

    suspend fun getOrderById(id: String): List<SelectFullOrderById> {
        return orderDao.getOrderById(id)
    }
}