package com.example.theapp.database.mapper

import com.example.theapp.ProductVariantEntity
import com.example.theapp.model.ProductVariant

fun ProductVariantEntity.toDomain() = ProductVariant(
    id,
    name,
    abbreviation
)

fun Iterable<ProductVariantEntity>.toDomain() = this.map {
    it.toDomain()
}