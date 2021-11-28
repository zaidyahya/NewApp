package com.example.theapp.repository

import com.example.theapp.SelectAllShoppingCartItemsWithDetails
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
        val rows = shoppingCartLocalDataSource.getAllShoppingCartItemsWithDetails()
        val shoppingCart = rows.map { rows ->
            ShoppingCart(
                productCharges = getProductCharges(rows),
                margin = null,
                cashToCollect = null,
                orderTotal = getProductCharges(rows),
                deliveryCharges = null,
                items = rows.map{
                    ShoppingCartItemWithDetails(
                        id = it.id,
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

    private fun getProductCharges(items: List<SelectAllShoppingCartItemsWithDetails>): Int {
        var productCharges = 0
        for(item in items) {
            productCharges += item.product_price * item.quantity
        }
        return productCharges
    }
}