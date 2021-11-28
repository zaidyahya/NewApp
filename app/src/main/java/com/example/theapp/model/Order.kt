package com.example.theapp.model

data class Order(
    val items: List<OrderItem>,
    val customer: Customer? = null,
    val status: String? = null
)
