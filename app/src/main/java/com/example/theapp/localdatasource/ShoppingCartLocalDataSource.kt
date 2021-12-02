package com.example.theapp.localdatasource

import com.example.theapp.SelectShoppingCart
import com.example.theapp.database.ShoppingCartDao
import com.example.theapp.database.mapper.toEntity
import com.example.theapp.model.ShoppingCartItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShoppingCartLocalDataSource @Inject constructor(
    private val shoppingCartDao: ShoppingCartDao
) {

    fun getShoppingCart(): Flow<List<SelectShoppingCart>> {
        return shoppingCartDao.getShoppingCart()
    }

    suspend fun updateShoppingCart(margin: Int?, cashToCollect: Int?) {
        shoppingCartDao.updateShoppingCart(margin, cashToCollect)
    }

    suspend fun insertShoppingCartItem(cartItem: ShoppingCartItem) {
        shoppingCartDao.insertShoppingCartItem(cartItem.toEntity())
    }

    suspend fun updateShoppingCartItem(cartItem: ShoppingCartItem) {
        shoppingCartDao.updateShoppingCartItem(cartItem.toEntity())
    }

    suspend fun deleteShoppingCartItem(id: String) =
        shoppingCartDao.deleteShoppingCartItem(id)

}