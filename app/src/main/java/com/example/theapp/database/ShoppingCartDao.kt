package com.example.theapp.database

import android.util.Log
import com.example.theapp.PhoneDatabase
import com.example.theapp.SelectShoppingCart
import com.example.theapp.ShoppingCartItemEntity
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShoppingCartDao @Inject constructor(
    private val database: PhoneDatabase
) {

    fun getShoppingCart(): Flow<List<SelectShoppingCart>> {
        //Log.d("WOT!", "${database.phoneDatabaseQueries.selectShoppingCart().executeAsList().size}")
        return database.phoneDatabaseQueries.selectShoppingCart().asFlow().mapToList()
    }

    suspend fun updateShoppingCart(margin: Int?, cashToCollect: Int?) {
        database.phoneDatabaseQueries.updateShoppingCart(
            id = "S1",
            margin = margin,
            cash_to_collect = cashToCollect
        )
    }

    /**
     * Making the ShoppingCart values null for OrderSummary page not for trigger on OrderSummary page because it did not enable to do it only on orderTotal change.
     * However, any change in the cart should make the old values redundant, and thus new ones should be required. It is not required most likely, but logically makes sense -> when cart is edited, make margin&CC null.
     */
    suspend fun insertShoppingCartItem(cartItem: ShoppingCartItemEntity) {
        database.phoneDatabaseQueries.transaction {
            database.phoneDatabaseQueries.insertOrReplaceShoppingCartItem(
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
            //database.phoneDatabaseQueries.updateShoppingCartItem(
            //    id = cartItem.id,
            //    product_variant_id = cartItem.product_variant_id,
            //    quantity = cartItem.quantity
            //)
            database.phoneDatabaseQueries.insertOrReplaceShoppingCartItem( // Because what if L&S exist and L was modified to S, thus should only have one resulting item
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