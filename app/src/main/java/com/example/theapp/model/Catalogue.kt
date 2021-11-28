package com.example.theapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Catalogue(
    val id: String,
    val name: String,
    val products: List<Product>
) : Parcelable
