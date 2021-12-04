package com.example.theapp.localdatasource

import com.example.theapp.database.CatalogueDao
import com.example.theapp.database.mapper.toDomain
import com.example.theapp.model.CatalogueNew
import com.example.theapp.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CatalogueLocalDataSource @Inject constructor(
    private val catalogueDao: CatalogueDao
) {

    suspend fun getCatalogues(): List<CatalogueNew> {
        return catalogueDao.getCatalogues()
    }

    fun getCategories() : List<Category> {
        return catalogueDao.getCategories().toDomain()
    }

    suspend fun getCataloguesByCategoryId(id: String): List<CatalogueNew> {
        return catalogueDao.getCataloguesByCategoryId(id)
    }
}