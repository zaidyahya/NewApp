package com.example.theapp.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val name: String,
    @DrawableRes
    val image: Int?,
    val imageUrl: String
) : Parcelable
