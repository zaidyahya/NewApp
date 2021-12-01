package com.example.theapp.ui.myorders.viewmodel

import androidx.lifecycle.*
import com.example.theapp.OrderEntity
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

    //var orders: LiveData<List<OrderSummary>> = orderNewRepository.getOrders().asLiveData() // Don't need Flow because it's not changing data + how to edit it?
    val orders = MutableLiveData<List<OrderSummary>>()

    val order = MutableLiveData<OrderNew>()

    val completedOrders = MutableLiveData<List<OrderSummary>>() //For Earnings fragment
    val placedOrder = orderNewRepository.getLastPlacedOrder().asLiveData()

    init {
        viewModelScope.launch {
            val response = orderNewRepository.getOrders()
            orders.postValue(response)
        }
    }

    fun getOrderById(id: String) {
        viewModelScope.launch {
            val response = orderNewRepository.getOrderById(id)
            order.postValue(response)
        }
    }

    private fun getOrdersByStatus(status: String) {
        viewModelScope.launch {
            val response = if(status == "All") {
                orderNewRepository.getOrders()
            } else {
                orderNewRepository.getOrdersByStatus(status)
            }
            orders.postValue(response)
        }
    }

    fun onChipSelected(status: String) {
        getOrdersByStatus(status)
    }

    fun getCompletedOrders() {
        viewModelScope.launch {
            val response = orderNewRepository.getOrdersByStatus("Completed")
            completedOrders.postValue(response)
        }
    }

}