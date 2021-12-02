package com.example.theapp.ui.checkout.ordersummary.fragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.theapp.R
import com.example.theapp.databinding.FragmentOrderSummaryBinding
import com.example.theapp.model.CustomerNew
import com.example.theapp.model.OrderItem
import com.example.theapp.model.ProductVariant
import com.example.theapp.model.ShoppingCartItemWithDetails
import com.example.theapp.ui.checkout.shoppingcart.adapter.ShoppingCartItemAdapter
import com.example.theapp.ui.checkout.shoppingcart.adapter.ShoppingCartItemNewAdapter
import com.example.theapp.ui.checkout.shoppingcart.fragment.ModifyProductBottomDialog
import com.example.theapp.ui.checkout.shoppingcart.viewmodel.ShoppingCartViewModel

/**
 * TODO :- No CashCollect dialog to be shown if modification is made without any actual modification
 */
class OrderSummaryFragment : Fragment(R.layout.fragment_order_summary), ShoppingCartItemNewAdapter.OnItemClickListener,
ModifyProductBottomDialog.OnItemClickListener, ModifyCashToCollectDialog.OnItemClickListener {

    private val viewModel: ShoppingCartViewModel by activityViewModels() // Because it's activityViewModels, @AndroidEntryPoint injection not needed i.e. it doesn't crash, not theoretically

    private var _binding: FragmentOrderSummaryBinding? = null
    private val binding get() = _binding!!

    private lateinit var shoppingCartAdapter: ShoppingCartItemAdapter
    private lateinit var shoppingCartAdapterNew: ShoppingCartItemNewAdapter

    private val args by navArgs<OrderSummaryFragmentArgs>()

    private var prevOrderTotal: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // To remove the cart icon when on this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrderSummaryBinding.bind(view)

        val customer: CustomerNew = args.customer

        //shoppingCartAdapter = ShoppingCartItemAdapter(this)
        shoppingCartAdapterNew = ShoppingCartItemNewAdapter(this)


        binding.apply {
            recyclerViewShoppingCart.apply {
                //setHasFixedSize(true)
                //adapter = shoppingCartAdapter
                adapter = shoppingCartAdapterNew
            }

            textViewCustomerName.text = customer.name
            textViewCustomerPhoneNumber.text = customer.phoneNumber
            textViewCustomerAddress.text = "${customer.addressLine1}, ${customer.addressLine2}, ${customer.city}, Pakistan"
            //binding.imageButtonEditAddress.setOnClickListener {
            //    activity?.let { ModifyAddressBottomDialog().show(it.supportFragmentManager, "ModifyAddressDialog") }
            //}

            binding.buttonPlaceOrder.setOnClickListener {
                onPlaceOrderButtonClick(customer)
            }
        }

        viewModel.shoppingCart.observe(viewLifecycleOwner) {
            Log.d("OSFragment - SCart", "$it")
            shoppingCartAdapterNew.submitList(it.items)

            if(prevOrderTotal != it.orderTotal && prevOrderTotal != -1) {
                showModifyCashCollectDialog()
            }

            binding.textViewProductChargesValue.text = "Rs. ${it.productCharges}"
            binding.textViewDeliveryChargesValue.text = "Rs. ${it.deliveryCharges}"
            binding.textViewOrderTotalValue.text = "Rs. ${it.orderTotal}"
            binding.textViewMarginValue.text = "Rs. ${it.margin}"
            binding.textViewCashCollectValue.text = "Rs. ${it.cashToCollect}"
        }

        viewModel.selectedCartItemProductVariants.observe(viewLifecycleOwner) { productVariants ->
            Log.d("OSFra - CartItemObserve", "$productVariants")
            showModifyCartItemDialog(viewModel.selectedCartItem, productVariants)
        }

        viewModel.placedOrderId.observe(viewLifecycleOwner) {
            Log.d("PlacedOrderId", it)
        }
    }

    // To remove the cart icon on this fragment
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // When cart item's edit button is clicked
    override fun onEditButtonClick(cartItem: ShoppingCartItemWithDetails) {
        viewModel.onEditButtonClick(cartItem)
    }

    // When remove button from ModifyProduct dialog is clicked
    override fun onRemoveButtonClick(id: String) {
        viewModel.deleteShoppingCartItem(id)
    }

    // When continue button from ModifyProduct dialog is clicked
    override fun onContinueButtonClick(size: String, quantity: Int) {
        /**
         * If button is clicked without any changes to values. Also don't need to show modifyCashColl values.
         */
        val productVariant: ProductVariant? =
            viewModel.selectedCartItemProductVariants.value?.find{ variant -> variant.abbreviation == size }

        productVariant?. let {
            prevOrderTotal = viewModel.shoppingCart.value?.orderTotal!!
            viewModel.updateShoppingCartItem(it.id, quantity)
        }
    }

    private fun onPlaceOrderButtonClick(customer: CustomerNew) {
        viewModel.onPlaceOrderButtonClick(customer)
        val action = OrderSummaryFragmentDirections.actionOrderSummaryFragmentToOrderPlacedFragment()
        findNavController().navigate(action)
    }

    private fun showModifyCartItemDialog(cartItem: ShoppingCartItemWithDetails, productVariants: List<ProductVariant>) {
        val sizes = productVariants.map{ it.abbreviation }
        val modifyCartItemDialog = ModifyProductBottomDialog(cartItem, sizes, this)
        activity?.let { modifyCartItemDialog.show(it.supportFragmentManager, "ModifyProductBottomDialog")}
    }

    private fun showModifyCashCollectDialog() {
        val modifyCashToCollectDialog = ModifyCashToCollectDialog(this)
        activity?.let { modifyCashToCollectDialog.show(it.supportFragmentManager, "ModifyCashToCollectDialog")}
        prevOrderTotal = -1
    }

    // When update button from ModifyCashCollect dialog is clicked
    override fun onUpdateButtonClick(value: Int) {
        viewModel.onUpdateButtonClick(value)
    }

}