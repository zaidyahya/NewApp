package com.example.theapp.repository

import com.example.theapp.localdatasource.OrderLocalDataSource
import com.example.theapp.model.CustomerNew
import com.example.theapp.model.OrderItemWithDetails
import com.example.theapp.model.OrderNew
import com.example.theapp.model.OrderSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrderNewRepository @Inject constructor(
    private val orderLocalDataSource: OrderLocalDataSource
) {

    fun getOrders(): Flow<List<OrderSummary>> {
        val rows = orderLocalDataSource.getOrders()
        val orders = rows.map { rows ->
                        rows.groupBy { it.id }
                        .map {
                            (orderId, orderEntry) ->
                            OrderSummary(
                                id = orderId,
                                datePlaced = orderEntry[0].date_placed,
                                status = orderEntry[0].status,
                                numItems = orderEntry.size,
                                customerName = orderEntry[0].customer_name
                            )
                        }
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


//        val order = rows.map { rows ->
//            OrderNew(
//                id = rows[0].id,
//                datePlaced = rows[0].date_placed,
//                status = rows[0].status,
//                margin = rows[0].margin,
//                cashToCollect = rows[0].cash_to_collect,
//                productCharges = rows[0].product_charges,
//                deliveryCharges = rows[0].delivery_charges,
//                orderTotal = rows[0].order_total,
//                items = rows.map {
//                    OrderItemWithDetails(
//                        id = it.order_item_id,
//                        quantity = it.quantity,
//                        productVariantId = it.product_variant_id,
//                        sizeAbbreviation = it.size_abbreviation,
//                        productId = it.product_id,
//                        productName = it.product_name,
//                        productPrice = it.product_price
//                    )
//                },
//                customer = CustomerNew(
//                    id = rows[0].customer_id,
//                    name = rows[0].customer_name,
//                    phoneNumber = rows[0].phone_number,
//                    addressLine1 = rows[0].address_line_1,
//                    addressLine2 = rows[0].address_line_2,
//                    city = rows[0].city,
//                    zipCode = rows[0].zip_code
//                )
//            )
//        }

        return order
    }

}