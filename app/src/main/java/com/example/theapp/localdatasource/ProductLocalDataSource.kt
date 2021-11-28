package com.example.theapp.localdatasource

import com.example.theapp.database.ProductDao
import com.example.theapp.database.mapper.toDomain
import com.example.theapp.model.ProductVariant
import javax.inject.Inject

class ProductLocalDataSource @Inject constructor(
    private val productDao: ProductDao
) {

    suspend fun getProductVariantsByProductId(id: String): List<ProductVariant> {
        return productDao.getProductVariantsByProductId(id).toDomain()
    }
}