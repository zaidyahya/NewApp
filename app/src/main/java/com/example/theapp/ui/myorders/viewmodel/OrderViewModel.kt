package com.example.theapp.ui.myorders.viewmodel

import androidx.lifecycle.*
import com.example.theapp.model.OrderNew
import com.example.theapp.model.OrderSummary
import com.example.theapp.repository.OrderNewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderNewRepository: OrderNewRepository
) : ViewModel() {

    val orders: LiveData<List<OrderSummary>> = orderNewRepository.getOrders().asLiveData()

    // Flow not required. Do with manual ID passing from OrderDetailsFragment & lateinit variable. Similar t
    val order = MutableLiveData<OrderNew>()

    fun getOrderById(id: String) {
        viewModelScope.launch {
            val response = orderNewRepository.getOrderById(id)
            order.postValue(response)
        }
    }
}