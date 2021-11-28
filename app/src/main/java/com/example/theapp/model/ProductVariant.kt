package com.example.theapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductVariant(
    val id: String,
    val name: String,
    val abbreviation: String
) : Parcelable
