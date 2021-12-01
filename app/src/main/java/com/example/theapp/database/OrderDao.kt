package com.example.theapp.database

import android.util.Log
import com.example.theapp.*
import com.example.theapp.model.OrderNew
import com.example.theapp.model.OrderSummary
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderDao @Inject constructor(
    private val database: PhoneDatabase
) {

//    fun getOrders(): Flow<List<SelectAllOrders>> {
//        return database.phoneDatabaseQueries.selectAllOrders().asFlow().mapToList()
//    }

    suspend fun getOrders(): List<SelectAllOrders> {
        return database.phoneDatabaseQueries.selectAllOrders().executeAsList()
    }

    suspend fun getOrderById(id: String): List<SelectFullOrderById> {
        return database.phoneDatabaseQueries.selectFullOrderById(id).executeAsList()
    }

    suspend fun getOrdersByStatus(status: String): List<SelectOrdersByStatus> {
        return database.phoneDatabaseQueries.selectOrdersByStatus(status).executeAsList()
    }

    suspend fun insertOrder(order: OrderNew) {
        database.phoneDatabaseQueries.transaction {

            database.phoneDatabaseQueries.insertOrder(
                id = order.id,
                margin = order.margin,
                cash_to_collect = order.cashToCollect,
                product_charges = order.productCharges,
                delivery_charges = order.deliveryCharges,
                order_total = order.orderTotal,
                customer_id = order.customer.id,
                status = order.status,
                date_placed = order.datePlaced
            )

            order.items.forEach{  item ->
                database.phoneDatabaseQueries.insertOrderItem(
                    id = item.id,
                    order_id = order.id,
                    product_variant_id = item.productVariantId,
                    quantity = item.quantity
                )
            }

            // Reset ShoppingCart
            database.phoneDatabaseQueries.deleteAllShoppingCartItems()
            database.phoneDatabaseQueries.updateShoppingCart(
                id = "S1",
                margin = null,
                cash_to_collect = null
            )
        }
    }

    suspend fun getLastInsertRowId(): Long {
        return database.phoneDatabaseQueries.lastOrderInsertRowId().executeAsOne()
    }

    fun getLastPlacedOrder(): Flow<List<SelectLastPlacedOrder>> {
        return database.phoneDatabaseQueries.selectLastPlacedOrder().asFlow().mapToList()
    }

}