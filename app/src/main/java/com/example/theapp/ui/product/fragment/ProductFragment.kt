package com.example.theapp.ui.product.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.theapp.MainActivity
import com.example.theapp.R
import com.example.theapp.databinding.FragmentProductBinding
import com.example.theapp.model.ProductNew
import com.example.theapp.model.ProductVariant
import com.example.theapp.ui.checkout.shoppingcart.viewmodel.ShoppingCartViewModel
import com.example.theapp.ui.product.adapter.ProductImagesAdapter

class ProductFragment : Fragment(R.layout.fragment_product), AddToCartBottomDialog.OnItemClickListener {

    private val viewModel: ShoppingCartViewModel by activityViewModels()

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ProductFragmentArgs>()
    //private lateinit var product: Product
    private lateinit var product: ProductNew

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProductBinding.bind(view)

        product = args.product
        val mainActivity = activity as MainActivity
        mainActivity.setActionBarTitle(product.name)

        binding.apply {
            viewPagerImages.adapter = ProductImagesAdapter()
            viewPagerTabs.setupWithViewPager(viewPagerImages, true)
            buttonAddToCart.setOnClickListener {
                showAddToCartDialog()
            }

            textViewName.text = product.name
            textViewPrice.text = "${product.price}"
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onContinueButtonClick(size: String, quantity: Int) {
        // Find the ProductVariant object that corresponds to the size
        val productVariant: ProductVariant? = product.variants.find{ variant -> variant.abbreviation == size }
        if (productVariant != null) {
            viewModel.insertShoppingCartItem(productVariant.id, quantity)
        }

        val action = ProductFragmentDirections.actionProductFragmentToShoppingCartFragment()
        findNavController().navigate(action)
    }

    private fun showAddToCartDialog() {
        val sizes = product.variants.map{ it.abbreviation }
        val addToCartBottomDialog = AddToCartBottomDialog(sizes,this)
        activity?.let { addToCartBottomDialog.show(it.supportFragmentManager, "AddToCartBottomDialog") }

    }
}