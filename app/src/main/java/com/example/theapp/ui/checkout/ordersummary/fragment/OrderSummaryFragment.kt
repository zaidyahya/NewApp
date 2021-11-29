package com.example.theapp.ui.checkout.ordersummary.fragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.theapp.R
import com.example.theapp.databinding.FragmentOrderSummaryBinding
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

    private val viewModel: ShoppingCartViewModel by activityViewModels()

    private var _binding: FragmentOrderSummaryBinding? = null
    private val binding get() = _binding!!

    private lateinit var shoppingCartAdapter: ShoppingCartItemAdapter
    private lateinit var shoppingCartAdapterNew: ShoppingCartItemNewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // To remove the cart icon when on this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrderSummaryBinding.bind(view)

        //shoppingCartAdapter = ShoppingCartItemAdapter(this)
        shoppingCartAdapterNew = ShoppingCartItemNewAdapter(this)


        binding.apply {
            recyclerViewShoppingCart.apply {
                //setHasFixedSize(true)
                //adapter = shoppingCartAdapter
                adapter = shoppingCartAdapterNew
            }

            textViewCustomerName.text = viewModel.order.value?.customer?.name
            //binding.imageButtonEditAddress.setOnClickListener {
            //    activity?.let { ModifyAddressBottomDialog().show(it.supportFragmentManager, "ModifyAddressDialog") }
            //}

            binding.buttonPlaceOrder.setOnClickListener {
                onPlaceOrderButtonClick()
            }
        }

        viewModel.shoppingCart.observe(viewLifecycleOwner) {
            Log.d("OSFragment - SCart", "$it")
            shoppingCartAdapterNew.submitList(it.items)

            if(it.margin == null || it.cashToCollect == null) {
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

    override fun onRemoveButtonClick(id: String) {
        viewModel.deleteShoppingCartItem(id)
    }

    override fun onContinueButtonClick(size: String, quantity: Int) {
        /**
         * If button is clicked without any changes to values. Also don't need to show modifyCashColl values.
         */
        val productVariant: ProductVariant? =
            viewModel.selectedCartItemProductVariants.value?.find{ variant -> variant.abbreviation == size }

        if (productVariant != null) {
            viewModel.updateShoppingCartItem(productVariant.id, quantity)
        }
    }

    // When continue button from ModifyProduct dialog is clicked
    private fun onPlaceOrderButtonClick() {
        //val action = OrderSummaryFragmentDirections.actionOrderSummaryFragmentToOrderPlacedFragment()
        //findNavController().navigate(action)
    }

    private fun showModifyCartItemDialog(cartItem: ShoppingCartItemWithDetails, productVariants: List<ProductVariant>) {
        val sizes = productVariants.map{ it.abbreviation }
        val modifyCartItemDialog = ModifyProductBottomDialog(cartItem, sizes, this)
        activity?.let { modifyCartItemDialog.show(it.supportFragmentManager, "ModifyProductBottomDialog")}
    }

    private fun showModifyCashCollectDialog() {
        val modifyCashToCollectDialog = ModifyCashToCollectDialog(this)
        activity?.let { modifyCashToCollectDialog.show(it.supportFragmentManager, "ModifyCashToCollectDialog")}
    }

    override fun onUpdateButtonClick(value: Int) {
        viewModel.onUpdateButtonClick(value)
    }

}