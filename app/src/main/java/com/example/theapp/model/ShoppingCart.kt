package com.example.theapp.model

data class ShoppingCart(
    val productCharges: Int?,
    var margin: Int?,
    var cashToCollect: Int?,
    val orderTotal: Int?,
    val deliveryCharges: Int?,
    val items: List<ShoppingCartItemWithDetails>
)
