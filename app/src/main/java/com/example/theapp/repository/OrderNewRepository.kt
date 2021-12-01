package com.example.theapp.repository

import com.example.theapp.OrderEntity
import com.example.theapp.localdatasource.OrderLocalDataSource
import com.example.theapp.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class OrderNewRepository @Inject constructor(
    private val orderLocalDataSource: OrderLocalDataSource
) {

    suspend fun getOrders(): List<OrderSummary> {
        val rows = orderLocalDataSource.getOrders()
        val orders = rows.groupBy { it.id }
                    .map {
                            (orderId, orderEntry) ->
                        OrderSummary(
                            id = orderId,
                            datePlaced = orderEntry[0].date_placed,
                            status = orderEntry[0].status,
                            numItems = orderEntry.size,
                            customerName = orderEntry[0].customer_name,
                            margin = orderEntry[0].margin
                        )
                    }

        return orders
    }

    suspend fun getOrderById(id: String): OrderNew {
        val rows = orderLocalDataSource.getOrderById(id)
        val order = OrderNew(
                id = rows[0].id,
                datePlaced = rows[0].date_placed,
                status = rows[0].status,
                margin = rows[0].margin,
                cashToCollect = rows[0].cash_to_collect,
                productCharges = rows[0].product_charges,
                deliveryCharges = rows[0].delivery_charges,
                orderTotal = rows[0].order_total,
                items = rows.map {
                    OrderItemWithDetails(
                        id = it.order_item_id,
                        quantity = it.quantity,
                        productVariantId = it.product_variant_id,
                        sizeAbbreviation = it.size_abbreviation,
                        productId = it.product_id,
                        productName = it.product_name,
                        productPrice = it.product_price
                    )
                },
                customer = CustomerNew(
                    id = rows[0].customer_id,
                    name = rows[0].customer_name,
                    phoneNumber = rows[0].phone_number,
                    addressLine1 = rows[0].address_line_1,
                    addressLine2 = rows[0].address_line_2,
                    city = rows[0].city,
                    zipCode = rows[0].zip_code
                )
            )

        return order
    }

    suspend fun getOrdersByStatus(status: String): List<OrderSummary> {
        val rows = orderLocalDataSource.getOrdersByStatus(status)
        val orders = rows.groupBy { it.id }
                    .map {
                            (orderId, orderEntry) ->
                        OrderSummary(
                            id = orderId,
                            datePlaced = orderEntry[0].date_placed,
                            status = orderEntry[0].status,
                            numItems = orderEntry.size,
                            customerName = orderEntry[0].customer_name,
                            margin = orderEntry[0].margin
                        )
                    }

        return orders
    }

    suspend fun insertOrder(margin: Int, cashToCollect: Int, productCharges: Int, deliveryCharges: Int, orderTotal: Int,
                            items: List<ShoppingCartItemWithDetails>, customer: CustomerNew) {

        val newOrder = OrderNew(
            id = UUID.randomUUID().toString(),
            datePlaced = "01-01-1996",
            status = "In Progress",
            margin = margin,
            cashToCollect = cashToCollect,
            productCharges = productCharges,
            deliveryCharges = deliveryCharges,
            orderTotal = orderTotal,
            items = items.map { OrderItemWithDetails(
                                id = UUID.randomUUID().toString(),
                                quantity = it.quantity,
                                productVariantId = it.productVariantId,
                                sizeAbbreviation = it.sizeAbbreviation,
                                productId = it.productId,
                                productName = it.productName,
                                productPrice = it.productPrice)
                              },
            customer = customer.copy()
        )

        orderLocalDataSource.insertOrder(newOrder)
    }

    suspend fun getLastOrderInsertedId(): String {
        return orderLocalDataSource.getLastOrderInsertedId()
    }

    fun getLastPlacedOrder(): Flow<OrderNew> {
        val rows = orderLocalDataSource.getLastPlacedOrder()
        val order = rows.map { rows ->
            OrderNew(
                id = rows[0].id,
                datePlaced = rows[0].date_placed,
                status = rows[0].status,
                margin = rows[0].margin,
                cashToCollect = rows[0].cash_to_collect,
                productCharges = rows[0].product_charges,
                deliveryCharges = rows[0].delivery_charges,
                orderTotal = rows[0].order_total,
                items = rows.map {
                    OrderItemWithDetails(
                        id = it.order_item_id,
                        quantity = it.quantity,
                        productVariantId = it.product_variant_id,
                        sizeAbbreviation = it.size_abbreviation,
                        productId = it.product_id,
                        productName = it.product_name,
                        productPrice = it.product_price
                    )
                },
                customer = CustomerNew(
                    id = rows[0].customer_id,
                    name = rows[0].customer_name,
                    phoneNumber = rows[0].phone_number,
                    addressLine1 = rows[0].address_line_1,
                    addressLine2 = rows[0].address_line_2,
                    city = rows[0].city,
                    zipCode = rows[0].zip_code
                )
            )
        }

        return order
    }


}