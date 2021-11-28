package com.example.theapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val houseNumber: String,
    val area: String,
    val city: String,
    val zipCode: String?,
    val landmark: String?
) : Parcelable
