package com.example.theapp.localdatasource

import com.example.theapp.database.CatalogueDao
import com.example.theapp.model.CatalogueNew
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatalogueLocalDataSource @Inject constructor(
    private val catalogueDao: CatalogueDao
) {

    fun getCatalogues(): Flow<List<CatalogueNew>> {
        return catalogueDao.getCatalogues()
    }
}