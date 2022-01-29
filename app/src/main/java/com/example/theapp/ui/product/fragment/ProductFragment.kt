package com.example.theapp.ui.product.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.FileProvider
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
import java.io.File

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
            textViewPrice.text = "Rs. ${product.price}"

            buttonShareNow.setOnClickListener {
                context?.let {
                    val imgOne = ImageView(context)
                    val drawable = AppCompatResources.getDrawable(it, R.drawable.kurta_1)
                    imgOne.setImageDrawable(drawable)

                    val imgTwo = ImageView(context)
                    val drawableTwo = AppCompatResources.getDrawable(it, R.drawable.orange_small)
                    imgTwo.setImageDrawable(drawableTwo)

                    onShareButtonClick(listOf(imgOne, imgTwo))
                }
            }
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

    private fun onShareButtonClick(images: List<ImageView>) {
        // For creation of files
//        for((index, image) in images.withIndex()) {
//            val drawable = image.drawable
//
//            val bmp = if(drawable is BitmapDrawable) {
//                drawable.bitmap
//            } else {
//                null
//            }
//
//            context?.let {
//                val file = File(it.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_$index.png")
//                val out = FileOutputStream(file)
//                bmp?.compress(Bitmap.CompressFormat.PNG, 90, out)
//                out.close()
//            }
//        }

        val pics = ArrayList<Uri>()
        for((index, image) in images.withIndex()) {
            context?.let {
                val file = File(it.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_$index.png")
                val uri = FileProvider.getUriForFile(it, "com.example.theapp.fileprovider", file)
                pics.add(uri)
            }
        }

        val intent = Intent()
        //intent.action = Intent.ACTION_SEND // Text x One Image
        intent.action = Intent.ACTION_SEND_MULTIPLE // Text x Multiple Images (or one)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.`package` = "com.whatsapp"
        intent.type = "image/*"
        //intent.putExtra(Intent.EXTRA_STREAM, uriUno) // Single Image
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, pics) // Multiple Images
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "")
        startActivity(intent)

    }
}