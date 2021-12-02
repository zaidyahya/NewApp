package com.example.theapp.ui.checkout.shoppingcart.viewmodel

import androidx.lifecycle.*
import com.example.theapp.model.*
import com.example.theapp.repository.IOrderRepository
import com.example.theapp.repository.OrderNewRepository
import com.example.theapp.repository.ShoppingCartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Direct to product page if clicked from cart
 * Prevent exit of ModifyCC dialog by clicking anywhere as it's required
 */
@HiltViewModel
class ShoppingCartViewModel @Inject constructor(
    private val orderRepository: IOrderRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val orderNewRepository: OrderNewRepository
) : ViewModel() {


    var order: LiveData<Order> = orderRepository.getOrder()
    val shoppingCart: LiveData<ShoppingCart> = shoppingCartRepository.getShoppingCart().asLiveData()

    lateinit var selectedCartItem: ShoppingCartItemWithDetails
    val selectedCartItemProductVariants = MutableLiveData<List<ProductVariant>>()

    val placedOrderId = MutableLiveData<String>()

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
        // Update only if there is a change in values
        if(productVariantId != selectedCartItem.productVariantId || quantity != selectedCartItem.quantity) {
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
    }

    fun deleteShoppingCartItem(id: String) {
        viewModelScope.launch {
            shoppingCartRepository.deleteShoppingCartItem(id)
        }
    }

    fun onContinueButtonClick(margin: Int, cashToCollect: Int) {
        viewModelScope.launch {
            shoppingCartRepository.updateShoppingCart(margin, cashToCollect)
        }
    }

    fun onUpdateButtonClick(cashToCollect: Int) {
        shoppingCart.value?.let {
            val margin = cashToCollect - it.orderTotal!!
            viewModelScope.launch {
                shoppingCartRepository.updateShoppingCart(margin, cashToCollect)
            }
        }
    }

    fun onPlaceOrderButtonClick(customer: CustomerNew) {
        shoppingCart.value?.let {
            viewModelScope.launch {
                orderNewRepository.insertOrder(
                    it.margin!!, it.cashToCollect!!, it.productCharges!!, it.deliveryCharges!!, it.orderTotal!!, it.items,
                    customer
                )
            }
        }
    }


}