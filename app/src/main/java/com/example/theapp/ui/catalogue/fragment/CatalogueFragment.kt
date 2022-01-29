package com.example.theapp.ui.catalogue.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.theapp.R
import com.example.theapp.databinding.FragmentCatalogueBinding
import com.example.theapp.model.ProductNew
import com.example.theapp.ui.catalogue.adapter.CatalogueItemAdapter
import java.io.File

class CatalogueFragment : Fragment(R.layout.fragment_catalogue),
    CatalogueItemAdapter.OnItemClickListener {

    private var _binding: FragmentCatalogueBinding? = null
    private val binding get() = _binding!!

    //private val productList = listOf( Product("Product 1", R.drawable.kurta_1), Product("Product 2", R.drawable.orange_small), Product("Product 3", R.drawable.green_big), Product("Product 4", R.drawable.kurta_1) )
    //private val catalogueAdapter = CatalogueItemAdapter(productList, this)
    //private lateinit var catalogueAdapter: CatalogueItemAdapter
    private lateinit var catalogueAdapter: CatalogueItemAdapter

    private val args by navArgs<CatalogueFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCatalogueBinding.bind(view)

        val catalogue = args.catalogue
        //catalogueAdapter = CatalogueItemAdapter(catalogue.products, this)
        catalogueAdapter = CatalogueItemAdapter(catalogue.products, this)

        binding.apply{

            recyclerViewCatalogue.apply {
                setHasFixedSize(true)
                //adapter = catalogueAdapter
                adapter = catalogueAdapter
            }

            buttonCopy.setOnClickListener {
                Log.d("HEY", "YAY!")
            }


            buttonShare.setOnClickListener {
                context?.let {
                    val imgOne = ImageView(context)
                    val drawable = AppCompatResources.getDrawable(it, R.drawable.kurta_1)
                    imgOne.setImageDrawable(drawable)

                    val imgTwo = ImageView(context)
                    val drawableTwo = AppCompatResources.getDrawable(it, R.drawable.orange_small)
                    imgTwo.setImageDrawable(drawableTwo)

                    onShareNowButtonClick(listOf(imgOne, imgTwo))
                }
            }

            textViewHeader.text = catalogue.name
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(product: ProductNew) {
        Log.d("CatalogueFragment", "onItemClick")
        val action = CatalogueFragmentDirections.actionCatalogueFragmentToProductFragment(product)
        findNavController().navigate(action)
    }

    override fun onShareNowButtonClick(images: List<ImageView>) {
        Log.d("CatalogueFragment", "onShareNowButtonClick")
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