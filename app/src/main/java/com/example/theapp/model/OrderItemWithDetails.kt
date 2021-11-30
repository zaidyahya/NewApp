package com.example.theapp.model

data class OrderItemWithDetails(
    val id: String,
    val quantity: Int,
    val productVariantId: String,
    val sizeAbbreviation: String,
    val productId: String,
    val productName: String,
    val productPrice: Int
)