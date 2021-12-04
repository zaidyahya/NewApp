package com.example.theapp.database.mapper

import com.example.theapp.CategoryEntity
import com.example.theapp.ProductVariantEntity
import com.example.theapp.model.Category
import com.example.theapp.model.ProductVariant

fun CategoryEntity.toDomain() = Category(
    id,
    name
)

fun Iterable<CategoryEntity>.toDomain() = this.map {
    it.toDomain()
}