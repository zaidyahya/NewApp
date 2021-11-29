package com.example.theapp.repository

import com.example.theapp.SelectShoppingCart
import com.example.theapp.localdatasource.ProductLocalDataSource
import com.example.theapp.localdatasource.ShoppingCartLocalDataSource
import com.example.theapp.model.ProductVariant
import com.example.theapp.model.ShoppingCart
import com.example.theapp.model.ShoppingCartItem
import com.example.theapp.model.ShoppingCartItemWithDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class ShoppingCartRepository @Inject constructor(
    private val shoppingCartLocalDataSource: ShoppingCartLocalDataSource,
    private val productLocalDataSource: ProductLocalDataSource
) {


    fun getShoppingCart(): Flow<ShoppingCart> {
        //val rows = shoppingCartLocalDataSource.getAllShoppingCartItemsWithDetails()
        val rows = shoppingCartLocalDataSource.getShoppingCart()
//        val shoppingCart = rows.map { rows ->
//            ShoppingCart(
//                productCharges = getProductCharges(rows),
//                margin = null,
//                cashToCollect = null,
//                orderTotal = getProductCharges(rows),
//                deliveryCharges = null,
//                items = rows.map{
//                    ShoppingCartItemWithDetails(
//                        id = it.id,
//                        quantity = it.quantity,
//                        productVariantId = it.product_variant_id,
//                        sizeAbbreviation = it.size_abbreviation,
//                        productId = it.product_id,
//                        productName = it.product_name,
//                        productPrice = it.product_price
//                    )
//                }
//            )
//        }

        val shoppingCart = rows.map { rows ->
            ShoppingCart(
                productCharges = getProductCharges(rows),
                margin = rows[0].margin,
                cashToCollect = rows[0].cash_to_collect,
                orderTotal = getProductCharges(rows),
                deliveryCharges = null,
                items = rows.map {
                    ShoppingCartItemWithDetails(
                        id = it.shopping_cart_item_id,
                        quantity = it.quantity,
                        productVariantId = it.product_variant_id,
                        sizeAbbreviation = it.size_abbreviation,
                        productId = it.product_id,
                        productName = it.product_name,
                        productPrice = it.product_price
                    )
                }
            )
        }

        return shoppingCart
    }

    //fun getShoppingCart(): Flow<ShoppingCart> {
    //    return shoppingCartLocalDataSource.getShoppingCart()
    //}

    suspend fun updateShoppingCart(
        margin: Int?, cashToCollect: Int?
    ) {
        // Not sending a ShoppingCart object (like with Item) because there will only be one thus only one ID
        shoppingCartLocalDataSource.updateShoppingCart(margin, cashToCollect)
    }

    suspend fun insertShoppingCartItem(
        productVariantId: String,
        quantity: Int
    ) {
        shoppingCartLocalDataSource.insertShoppingCartItem(
            ShoppingCartItem(
                id = UUID.randomUUID().toString(),
                productVariantId = productVariantId,
                quantity = quantity
            )
        )
    }

    suspend fun updateShoppingCartItem(cartItem: ShoppingCartItem) {
        shoppingCartLocalDataSource.updateShoppingCartItem(cartItem)
    }

    suspend fun deleteShoppingCartItem(id: String) {
        shoppingCartLocalDataSource.deleteShoppingCartItem(id)
    }

    suspend fun getProductVariantsByProductId(id: String): List<ProductVariant> {
        return productLocalDataSource.getProductVariantsByProductId(id)
    }

    private fun getProductCharges(items: List<SelectShoppingCart>): Int {
        var productCharges = 0
        for(item in items) {
            productCharges += item.product_price * item.quantity
        }
        return productCharges
    }

    fun updateMargin(margin: Int, cashToCollect: Int?) {

    }
}