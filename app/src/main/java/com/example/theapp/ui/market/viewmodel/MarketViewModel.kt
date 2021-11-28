package com.example.theapp.ui.market.viewmodel

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.theapp.model.Catalogue
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
    var newCatalogues = catalogueRepository.getCataloguesNew().asLiveData()

    val pagingCatalogues = currentQuery.switchMap { queryString ->
        catalogueRepository.getSearchResults(queryString).cachedIn(viewModelScope)
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