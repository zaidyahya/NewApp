package com.example.theapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Customer(
    val id: String,
    val name: String,
    val isSelected: Boolean,
    val address: Address?
) : Parcelable
