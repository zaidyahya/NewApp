package com.example.theapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatalogueNew(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val products: List<ProductNew>
) : Parcelable
