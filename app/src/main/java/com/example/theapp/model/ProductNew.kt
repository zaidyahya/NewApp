package com.example.theapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductNew(
    val id: String,
    val name: String,
    val price: Int,
    val variants: List<ProductVariant>
) : Parcelable
