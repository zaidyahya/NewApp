package com.example.theapp.localdatasource

import com.example.theapp.*
import com.example.theapp.database.OrderDao
import com.example.theapp.model.OrderNew
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderLocalDataSource @Inject constructor(
    private val orderDao: OrderDao
) {

//    fun getOrders(): Flow<List<SelectAllOrders>> {
//        return orderDao.getOrders()
//    }

    suspend fun getOrders(): List<SelectAllOrders> {
        return orderDao.getOrders()
    }

    suspend fun getOrderById(id: String): List<SelectFullOrderById> {
        return orderDao.getOrderById(id)
    }

    suspend fun getOrdersByStatus(status: String): List<SelectOrdersByStatus> {
        return orderDao.getOrdersByStatus(status)
    }

    suspend fun insertOrder(order: OrderNew) {
        orderDao.insertOrder(order)
    }

    suspend fun getLastOrderInsertedId(): String {
        return orderDao.getLastInsertRowId().toString()
    }

    fun getLastPlacedOrder(): Flow<List<SelectLastPlacedOrder>> {
        return orderDao.getLastPlacedOrder()
    }

}