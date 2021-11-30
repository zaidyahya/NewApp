package com.example.theapp.model

data class OrderSummary(
    val id: String,
    val datePlaced: String,
    val status: String,
    val numItems: Int,
    val customerName: String
)
