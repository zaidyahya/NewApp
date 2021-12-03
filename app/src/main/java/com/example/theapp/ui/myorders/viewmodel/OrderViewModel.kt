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

    //var orders: LiveData<List<OrderSummary>> = orderNewRepository.getOrders().asLiveData() // Don't need Flow because it's not changing data + how to edit it? Void; need Flow due to order cancelling.
    val orders = MutableLiveData<List<OrderSummary>>()
    val ordersFlow : LiveData<List<OrderSummary>> = orderNewRepository.getOrdersFlow().asLiveData()
    val ordersToDisplay  = MutableLiveData<List<OrderSummary>>()

    //var order = MutableLiveData<OrderNew>()
    lateinit var order: LiveData<OrderNew>

    val completedOrders = MutableLiveData<List<OrderSummary>>() //For Earnings fragment
    val placedOrder = orderNewRepository.getLastPlacedOrder().asLiveData()

    // This variable is relied upon to populate ordersToDisplay appropriately. It's to cater for 'Back' navigation to Fragment and what to show based on selectedChip.
    // On 'Back' navigate, the Chip remains on previous selection. Thus we want to show the orders for that selection.
    // Since orders are Flow, we look at this variable to know the previous/current selection and then display the list accordingly.
    var selectedChipText = "All"

    init {
        //viewModelScope.launch {
        //    val response = orderNewRepository.getOrders()
        //    orders.postValue(response)
        //}
    }

    fun getOrderById(id: String) {
        //viewModelScope.launch {
        //    val response = orderNewRepository.getOrderById(id)
        //    order.postValue(response)
        //}
        order = orderNewRepository.getOrderFlowById(id).asLiveData()
    }

    private fun getOrdersByStatus(status: String) : List<OrderSummary>? {
        //viewModelScope.launch {
        //    val response = if(status == "All") {
        //        orderNewRepository.getOrders()
        //    } else {
        //        orderNewRepository.getOrdersByStatus(status)
        //    }
        //    orders.postValue(response)
        //}

        ordersFlow.value?.let {
            return when (status) {
                "All" -> it
                else -> it.filter { order -> order.status == status }
            }
        }

        return null
    }

    fun onChipSelected(status: String) {
        selectedChipText = status
        updateOrdersToDisplay()
    }

    fun getCompletedOrders() {
        viewModelScope.launch {
            val response = orderNewRepository.getOrdersByStatus("Completed")
            completedOrders.postValue(response)
        }
    }

    fun onCancelButtonClick(id: String) {
        viewModelScope.launch {
            orderNewRepository.updateOrderStatus(id, "Cancelled")
        }
    }

    fun updateOrdersToDisplay() {
        ordersToDisplay.value = getOrdersByStatus(selectedChipText)
    }

}