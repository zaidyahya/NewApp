package com.example.theapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.theapp.R
import com.example.theapp.api.UnsplashApi
import com.example.theapp.localdatasource.CatalogueLocalDataSource
import com.example.theapp.model.Catalogue
import com.example.theapp.model.CatalogueNew
import com.example.theapp.model.Product
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatalogueRepository @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val catalogueLocalDataSource: CatalogueLocalDataSource
) : ICatalogueRepository {

    val productListOne = listOf(Product("Product 1", R.drawable.kurta_1, ""), Product("Product 2", R.drawable.orange_small, ""))
    val productListTwo = listOf(Product("Product 3", R.drawable.green_big, ""), Product("Product 4", R.drawable.kurta_1, ""))

    private var mockCatalogueList = MutableLiveData(listOf(
        Catalogue(UUID.randomUUID().toString(),"Ravishing Shalwar Suit", productListOne), Catalogue(UUID.randomUUID().toString(),"Flamboyant Kurtis", productListTwo),
        Catalogue(UUID.randomUUID().toString(),"Extravagant Shalwars", productListTwo), Catalogue(UUID.randomUUID().toString(),"Rocking Kurtis", productListOne)
    ))

    override fun getCatalogues(): LiveData<List<Catalogue>> {
        Log.d("CatalogueRepository", "GetCatalogues")
        return mockCatalogueList
    }

    override fun getSearchResults(query: String): LiveData<PagingData<Catalogue>> {
        Log.d("Repository", "SearchResults")
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashPagingSource(unsplashApi, query) }
        ).liveData
    }

    override fun getCataloguesNew(): Flow<List<CatalogueNew>> {
        return catalogueLocalDataSource.getCatalogues()
    }

}