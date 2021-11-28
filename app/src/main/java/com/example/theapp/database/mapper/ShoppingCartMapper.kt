package com.example.theapp.database.mapper

import com.example.theapp.ShoppingCartItemEntity
import com.example.theapp.model.ShoppingCartItem

fun ShoppingCartItem.toEntity() = ShoppingCartItemEntity(
    id,
    productVariantId,
    quantity
)