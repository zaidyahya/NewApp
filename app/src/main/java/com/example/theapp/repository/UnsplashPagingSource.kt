package com.example.theapp.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.theapp.R
import com.example.theapp.api.UnsplashApi
import com.example.theapp.model.Catalogue
import com.example.theapp.model.Product
import com.example.theapp.model.UnsplashPhoto
import retrofit2.HttpException
import java.io.IOException
import java.util.*

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class UnsplashPagingSource(
    private val unsplashApi: UnsplashApi,
    private val query: String
) : PagingSource<Int, Catalogue>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Catalogue> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = unsplashApi.searchPhotos(query, position, params.loadSize)
            val photos = response.results

            val listCatalogues = createCatalogues(photos)

            LoadResult.Page(
                data = listCatalogues,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            Log.d("PagingSource", "IOException")
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.d("PagingSource", "HttpException")
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Catalogue>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


    /**
     * Helper function to create Catalogue objects for Paging from an API call that gets photos
     */
    private fun createCatalogues(photos: List<UnsplashPhoto>) : List<Catalogue> {
        val listCatalogues: MutableList<Catalogue> = mutableListOf()
        for(photo in photos) {
            val catalogue = Catalogue(UUID.randomUUID().toString(), photo.user.name, listOf(Product("Nothing", R.drawable.kurta_1, photo.urls.regular), Product("Nothing", R.drawable.green_big, photo.urls.regular)))
            listCatalogues.add(catalogue)
        }

        return listCatalogues
    }

}