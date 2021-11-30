package com.example.theapp.model

data class OrderNew(
    val id: String,
    val datePlaced: String,
    val status: String,
    val margin: Int,
    val cashToCollect: Int,
    val productCharges: Int,
    val deliveryCharges: Int,
    val orderTotal: Int,
    val items: List<OrderItemWithDetails>,
    val customer: CustomerNew
)
