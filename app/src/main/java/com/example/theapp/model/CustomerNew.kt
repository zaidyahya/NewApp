package com.example.theapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomerNew(
    val id: String,
    var isSelected: Boolean = false,
    val name: String,
    val phoneNumber: String,
    val addressLine1: String,
    val addressLine2: String,
    val city: String,
    val zipCode: String?
) : Parcelable