package com.example.theapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.theapp.model.Catalogue
import com.example.theapp.model.CatalogueNew
import kotlinx.coroutines.flow.Flow

interface ICatalogueRepository {

    fun getCatalogues(): LiveData<List<Catalogue>>
    fun getSearchResults(query: String): LiveData<PagingData<Catalogue>>

    fun getCataloguesNew(): Flow<List<CatalogueNew>>
}