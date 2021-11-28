package com.example.theapp.ui.checkout.shoppingcart.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.theapp.model.*
import com.example.theapp.repository.IOrderRepository
import com.example.theapp.repository.ShoppingCartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingCartViewModel @Inject constructor(
    private val orderRepository: IOrderRepository,
    private val shoppingCartRepository: ShoppingCartRepository
) : ViewModel() {


    var order: LiveData<Order> = orderRepository.getOrder()
    val shoppingCart: LiveData<ShoppingCart> = shoppingCartRepository.getShoppingCart().asLiveData()

    lateinit var selectedCartItem: ShoppingCartItemWithDetails
    val selectedCartItemProductVariants = MutableLiveData<List<ProductVariant>>()

    fun addOrderItem(product: Product, size: String) {
        order = orderRepository.addOrderItem(product, size)
    }

    fun removeOrderItem(id: String) {
        //Log.d("ViewModel", "Remove")
        order = orderRepository.deleteOrderItem(id)
    }

    fun updateOrderItem(id: String, size: String) {
        order = orderRepository.updateOrderItem(id, size)
    }

    fun addCustomerToOrderItem(customer: Customer) {
        order = orderRepository.addCustomer(customer)
    }

    fun insertShoppingCartItem(
        productVariantId: String,
        quantity: Int
    ) {
        viewModelScope.launch {
            shoppingCartRepository.insertShoppingCartItem(productVariantId, quantity)
        }
    }

    fun onEditButtonClick(cartItemWithDetails: ShoppingCartItemWithDetails) {
        selectedCartItem = cartItemWithDetails
        getProductVariantsByProductId(selectedCartItem.productId)
    }

    private fun getProductVariantsByProductId(id: String){
        viewModelScope.launch{
            val response = shoppingCartRepository.getProductVariantsByProductId(id)
            selectedCartItemProductVariants.postValue(response)
        }
    }

    fun testMethod(): LiveData<List<ProductVariant>> {
        val result = MutableLiveData<List<ProductVariant>>()
        viewModelScope.launch {
            val response = shoppingCartRepository.getProductVariantsByProductId("P2")
            result.postValue(response)
        }
        return result
    }

    fun updateShoppingCartItem(
        productVariantId: String,
        quantity: Int
    ) {
        viewModelScope.launch {
            shoppingCartRepository.updateShoppingCartItem(
                ShoppingCartItem(
                    id = selectedCartItem.id,
                    productVariantId = productVariantId,
                    quantity = quantity
                )
            )
        }
    }

    fun deleteShoppingCartItem(id: String) {
        viewModelScope.launch {
            shoppingCartRepository.deleteShoppingCartItem(id)
        }
    }

    fun onContinueButtonClick(margin: Int, cashToCollect: Int) {
        shoppingCart.value?.let {
            it.margin = margin
            it.cashToCollect = cashToCollect
        }
        Log.d("SIUUU", "${shoppingCart.value}")
    }


}