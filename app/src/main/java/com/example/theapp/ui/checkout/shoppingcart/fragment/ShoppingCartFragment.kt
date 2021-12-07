package com.example.theapp.ui.checkout.shoppingcart.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.theapp.MainActivity
import com.example.theapp.R
import com.example.theapp.databinding.FragmentShoppingCartBinding
import com.example.theapp.model.ProductVariant
import com.example.theapp.model.ShoppingCartItemWithDetails
import com.example.theapp.ui.checkout.shoppingcart.adapter.ShoppingCartItemAdapter
import com.example.theapp.ui.checkout.shoppingcart.adapter.ShoppingCartItemNewAdapter
import com.example.theapp.ui.checkout.shoppingcart.viewmodel.ShoppingCartViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * TODO :- MarginEarn textcolor change. Checks such as :- negative margin, empty cash collect. When error is there, remove upon retype. Error icon takes too much space
 * TODO :- Go to Product page from individual product in cart. Why? If one wants to add a different size, would have to browse again to find it.
 * TODO :- Images + Images Scroller
 * TODO :- Search bar, Category filtering, Navigation (Anim + Headers + Pop-Ups), Dialog TextInputs Fixing
 */
@AndroidEntryPoint
class ShoppingCartFragment : Fragment(R.layout.fragment_shopping_cart), ShoppingCartItemNewAdapter.OnItemClickListener,
ModifyProductBottomDialog.OnItemClickListener {

    //private val viewModel: ShoppingCartViewModel by viewModels()
    private val viewModel: ShoppingCartViewModel by activityViewModels() //https://developer.android.com/guide/fragments/communicate

    private var _binding: FragmentShoppingCartBinding? = null
    private val binding get() = _binding!!

    //private val productList = listOf( Product("Product 1", R.drawable.kurta_1), Product("Product 2", R.drawable.orange_small), Product("Product 3", R.drawable.green_big), Product("Product 4", R.drawable.kurta_1), Product("Product 3", R.drawable.green_big) )
    //private val shoppingCartAdapter = ShoppingCartItemAdapter(productList, this)
    //private val shoppingCartAdapter = ShoppingCartItemAdapter(this)
    private lateinit var shoppingCartAdapter: ShoppingCartItemAdapter
    private lateinit var shoppingCartAdapterNew: ShoppingCartItemNewAdapter

    //private val modifyProductBottomDialog = ModifyProductBottomDialog()

    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // To remove the cart icon when on this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentShoppingCartBinding.bind(view)

        //mainActivity = activity as MainActivity
        //val orderItems = mainActivity.cartViewModel.order.value!!.items
        //shoppingCartAdapter = ShoppingCartItemAdapter(orderItems, this)
        //shoppingCartAdapter = ShoppingCartItemAdapter(this)
        shoppingCartAdapterNew = ShoppingCartItemNewAdapter(this)

        binding.apply {
            recyclerViewShoppingCart.apply {
                //setHasFixedSize(true) //Sets dimensions with initial # of viewModel elements. Thus is not able to show the updated ones. Only works with match_parent because the dims = screen then so enough space. Maybe not required when 'loading' is included with API calls.
                //adapter = shoppingCartAdapter
                adapter = shoppingCartAdapterNew
            }

            textInputLayoutCashCollectValue.doOnTextChanged { text, start, before, count ->
                textInputLayoutCashCollect.isErrorEnabled = false
                val calculatedMargin = calculateMargin("$text")
                textViewMarginValue.text = "Rs. ${calculatedMargin}"
                if(calculatedMargin <= 0) {
                    textViewMargin.setTextColor(Color.parseColor("#d32f2f"))
                    textViewMarginValue.setTextColor(Color.parseColor("#d32f2f"))
                } else {
                    textViewMargin.setTextColor(Color.parseColor("#388e3c"))
                    textViewMarginValue.setTextColor(Color.parseColor("#388e3c"))
                }
            }

            buttonContinue.setOnClickListener {
                val margin = textViewMarginValue.text.toString().replace("Rs. ", "").toInt()
                val textCashCollect = textInputLayoutCashCollectValue.text.toString().replace("Rs. ", "")

                if(textCashCollect == "" || margin <= 0) {
                    textInputLayoutCashCollect.isErrorEnabled = true
                    textInputLayoutCashCollect.error = "Amount is too low"
                } else {
                    val cashToCollect = textCashCollect.toInt()
                    viewModel.onContinueButtonClick(margin, cashToCollect)
                    val action = ShoppingCartFragmentDirections.actionShoppingCartFragmentToPickAddressFragment()
                    findNavController().navigate(action)
                }
            }
        }

       //viewModel.order.observe(viewLifecycleOwner) {
       //     Log.d("ShoppingCartFragment", "Observed")
            //shoppingCartAdapter.setOrderItems(it.items)
        //}

        viewModel.shoppingCart.observe(viewLifecycleOwner) {
            Log.d("SCart", "$it")
            shoppingCartAdapterNew.submitList(it.items)

            binding.textViewProductChargesValue.text = "Rs. ${it.productCharges}"
            binding.textViewOrderTotalValue.text = "Rs. ${it.orderTotal}"
        }

        /**
         * TODO :- Fix this recurring display
         */
        viewModel.selectedCartItemProductVariants.observe(viewLifecycleOwner) { productVariants ->
            Log.d("I AM HERE", "$productVariants")
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
        Log.d("How many times?", "??")
        viewModel.onEditButtonClick(cartItem)
    }

    private fun showModifyCartItemDialog(cartItem: ShoppingCartItemWithDetails, productVariants: List<ProductVariant>) {
        val sizes = productVariants.map{ it.abbreviation }
        val modifyCartItemDialog = ModifyProductBottomDialog(cartItem, sizes, this)
        activity?.let { modifyCartItemDialog.show(it.supportFragmentManager, "ModifyProductBottomDialog")}
    }

    // When continue button from ModifyProduct dialog is clicked
    override fun onContinueButtonClick(size: String, quantity: Int) {
        val productVariant: ProductVariant? =
            viewModel.selectedCartItemProductVariants.value?.find{ variant -> variant.abbreviation == size }

        if (productVariant != null) {
            viewModel.updateShoppingCartItem(productVariant.id, quantity)
        }
    }

    // When remove button from ModifyProduct dialog is clicked
    override fun onRemoveButtonClick(id: String) {
        viewModel.deleteShoppingCartItem(id)
    }

    private fun calculateMargin(valueEntered: CharSequence) : Int {
        if(valueEntered.isNotEmpty()) {
            return "$valueEntered".toInt() - viewModel.shoppingCart.value?.productCharges!!
        }
        return 0
    }
}

