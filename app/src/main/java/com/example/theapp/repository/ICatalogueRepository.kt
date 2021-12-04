package com.example.theapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.theapp.model.Catalogue
import com.example.theapp.model.CatalogueNew
import com.example.theapp.model.Category
import kotlinx.coroutines.flow.Flow

interface ICatalogueRepository {

    fun getCatalogues(): LiveData<List<Catalogue>>
    fun getSearchResults(query: String): LiveData<PagingData<Catalogue>>

    suspend fun getCataloguesNew(): List<CatalogueNew>
    fun getCategories(): List<Category>
    suspend fun getCataloguesByCategoryId(id: String): List<CatalogueNew>
}