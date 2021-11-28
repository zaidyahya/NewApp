package com.example.theapp.database

import com.example.theapp.PhoneDatabase
import com.example.theapp.ProductVariantEntity
import javax.inject.Inject

class ProductDao @Inject constructor(
    private val database: PhoneDatabase
) {

    suspend fun getProductVariantsByProductId(id: String): List<ProductVariantEntity> {
        return database.phoneDatabaseQueries.selectProductVariantsByProductId(id).executeAsList()
    }
}