package com.example.theapp.database

import com.example.theapp.PhoneDatabase
import com.example.theapp.SelectAllShoppingCartItemsWithDetails
import com.example.theapp.SelectShoppingCart
import com.example.theapp.ShoppingCartItemEntity
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShoppingCartDao @Inject constructor(
    private val database: PhoneDatabase
) {

//    fun getShoppingCart() : Flow<ShoppingCart> { // Include orderTotal. Remove other fields (?)
//        Log.d("GETCART","SCDao")
//        val rows = database.phoneDatabaseQueries.selectShoppingCart("S1").asFlow().mapToList()
//        val shoppingCart = rows.map { rows ->
//            ShoppingCart(
//                id = rows[0].id,
//                productCharges = "${getProductCharges(rows)}",
//                margin = rows[0].margin,
//                cashToCollect = rows[0].cash_to_collect,
//                deliveryCharges = rows[0].delivery_charges,
//                items = rows.map{
//                    ShoppingCartItemWithDetails(
//                        id = it.shopping_cart_item_id,
//                        quantity = it.quantity,
//                        productVariantId = "NULL",
//                        sizeAbbreviation = it.size_abbreviation,
//                        productId = it.product_id,
//                        productName = it.product_name,
//                        productPrice = it.product_price
//                    )
//                }
//            )
//        }
//
//        return shoppingCart
//    }

    fun getShoppingCart(): Flow<List<SelectShoppingCart>> {
        return database.phoneDatabaseQueries.selectShoppingCart().asFlow().mapToList()
    }

    suspend fun updateShoppingCart(margin: Int?, cashToCollect: Int?) {
        database.phoneDatabaseQueries.updateShoppingCart(
            id = "S1",
            margin = margin,
            cash_to_collect = cashToCollect
        )
    }

    fun getAllShoppingCartItemsWithDetails(): Flow<List<SelectAllShoppingCartItemsWithDetails>> {
        return database.phoneDatabaseQueries.selectAllShoppingCartItemsWithDetails().asFlow().mapToList()
    }

    /**
     * Making the ShoppingCart values null for OrderSummary page so that nullability can be the trigger to show the 'enter new cash collect' dialog
     * Besides, any change in the cart should make the old values redundant, and thus new ones should be required.
     */
    suspend fun insertShoppingCartItem(cartItem: ShoppingCartItemEntity) {
        database.phoneDatabaseQueries.transaction {
            database.phoneDatabaseQueries.insertShoppingCartItem(
                id = cartItem.id,
                shopping_cart_id = cartItem.shopping_cart_id,
                product_variant_id = cartItem.product_variant_id,
                quantity = cartItem.quantity
            )
            database.phoneDatabaseQueries.updateShoppingCart(
                id = "S1",
                margin = null,
                cash_to_collect = null
            )
        }
    }

    suspend fun updateShoppingCartItem(cartItem: ShoppingCartItemEntity) {
        database.phoneDatabaseQueries.transaction {
            database.phoneDatabaseQueries.updateShoppingCartItem(
                id = cartItem.id,
                product_variant_id = cartItem.product_variant_id,
                quantity = cartItem.quantity
            )
            database.phoneDatabaseQueries.updateShoppingCart(
                id = "S1",
                margin = null,
                cash_to_collect = null
            )
        }
    }

    suspend fun deleteShoppingCartItem(id: String)  {
        database.phoneDatabaseQueries.transaction {
            database.phoneDatabaseQueries.deleteShoppingCartItem(id)
            database.phoneDatabaseQueries.updateShoppingCart(
                id = "S1",
                margin = null,
                cash_to_collect = null
            )
        }
    }

}