package com.example.theapp.ui.market.viewmodel

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.theapp.model.Catalogue
import com.example.theapp.model.CatalogueNew
import com.example.theapp.model.Category
import com.example.theapp.repository.ICatalogueRepository
import com.example.theapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val catalogueRepository: ICatalogueRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    var catalogues: LiveData<List<Catalogue>> = catalogueRepository.getCatalogues()
    //var user = userRepository.getUser()
    //val newCatalogues = catalogueRepository.getCataloguesNew().asLiveData()

    val newCatalogues = MutableLiveData<List<CatalogueNew>>()

    //val categories = catalogueRepository.getCategories().asLiveData()
    val categories = MutableLiveData<List<Category>>()

    init {
        viewModelScope.launch {
            val response = catalogueRepository.getCataloguesNew()
            val catResponse = catalogueRepository.getCategories()
            newCatalogues.postValue(response)
            categories.postValue(catResponse)
        }
    }

    val pagingCatalogues = currentQuery.switchMap { queryString ->
        catalogueRepository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun onCategoryDropdownChange(name: String) {
        if(name == "All") {
            viewModelScope.launch {
                val response = catalogueRepository.getCataloguesNew()
                newCatalogues.postValue(response)
            }
        }
        else {
            val category = categories.value?.find { it.name == name }
            category?.let {
                viewModelScope.launch {
                    val response = catalogueRepository.getCataloguesByCategoryId(it.id)
                    newCatalogues.postValue(response)
                }
            }
        }
    }

    fun insert() {
        viewModelScope.launch {
            userRepository.insertUser("Shawshank", "Redemption", "2451231")
        }
    }

    companion object {
        private const val DEFAULT_QUERY = "pants"
    }
}