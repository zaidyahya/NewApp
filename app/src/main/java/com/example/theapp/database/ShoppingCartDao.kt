package com.example.theapp.database

import com.example.theapp.PhoneDatabase
import com.example.theapp.SelectAllShoppingCartItemsWithDetails
import com.example.theapp.ShoppingCartItemEntity
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShoppingCartDao @Inject constructor(
    private val database: PhoneDatabase
) {

    //suspend fun updateShoppingCart(cart: ShoppingCartEntity) {
    //    database.phoneDatabaseQueries.updateShoppingCart(
    //        id = cart.id,
    //        product_charges = cart.product_charges
    //    )
    //}

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

    fun getAllShoppingCartItemsWithDetails(): Flow<List<SelectAllShoppingCartItemsWithDetails>> {
        return database.phoneDatabaseQueries.selectAllShoppingCartItemsWithDetails().asFlow().mapToList()
    }

    suspend fun insertShoppingCartItem(cartItem: ShoppingCartItemEntity) {
        database.phoneDatabaseQueries.insertShoppingCartItem(
            id = cartItem.id,
            product_variant_id = cartItem.product_variant_id,
            quantity = cartItem.quantity
        )
    }

    suspend fun updateShoppingCartItem(cartItem: ShoppingCartItemEntity) {
        database.phoneDatabaseQueries.updateShoppingCartItem(
            id = cartItem.id,
            product_variant_id = cartItem.product_variant_id,
            quantity = cartItem.quantity
        )
    }

    suspend fun deleteShoppingCartItem(id: String) = database.phoneDatabaseQueries.deleteShoppingCartItem(id)

}