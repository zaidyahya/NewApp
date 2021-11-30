package com.example.theapp.database

import com.example.theapp.PhoneDatabase
import com.example.theapp.SelectAllOrders
import com.example.theapp.SelectFullOrderById
import com.example.theapp.model.OrderSummary
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderDao @Inject constructor(
    private val database: PhoneDatabase
) {

    fun getOrders(): Flow<List<SelectAllOrders>> {
        return database.phoneDatabaseQueries.selectAllOrders().asFlow().mapToList()
    }

    suspend fun getOrderById(id: String): List<SelectFullOrderById> {
        return database.phoneDatabaseQueries.selectFullOrderById(id).executeAsList()
    }
}