package com.example.theapp.ui.market.fragment

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.theapp.R
import com.example.theapp.databinding.FragmentMarketBinding
import com.example.theapp.model.Catalogue
import com.example.theapp.model.CatalogueNew
import com.example.theapp.ui.market.adapter.BackupItemAdapter
import com.example.theapp.ui.market.adapter.BackupItemAdapterNew
import com.example.theapp.ui.market.adapter.MarketItemAdapter
import com.example.theapp.ui.market.viewmodel.MarketViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream


/**
 * TODO :- Fixing of item_market UI
 */
@AndroidEntryPoint
class MarketFragment : Fragment(R.layout.fragment_market), BackupItemAdapter.OnItemCLickListener, MarketItemAdapter.OnItemCLickListener, BackupItemAdapterNew.OnItemClickListener {

    private val viewModel: MarketViewModel by viewModels()

    private var _binding: FragmentMarketBinding? = null
    private val binding get() = _binding!!

    private val backupAdapter = BackupItemAdapter(this)
    private val backupAdapterNew = BackupItemAdapterNew(this)
    private val marketAdapter = MarketItemAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMarketBinding.bind(view)

        binding.apply {
            recyclerViewMarket.apply {
                setHasFixedSize(true)
                //adapter = backupAdapter
                //adapter = marketAdapter
                adapter = backupAdapterNew
            }

            val categories = listOf("Kurti", "Shalwar", "Pant")
//            val adapter = ArrayAdapter(requireContext(), R.layout.item_size_menu, categories)
//            val autoCompleteTextView = testCategoriesInputLayout.editText as? AutoCompleteTextView
//            autoCompleteTextView?.setAdapter(adapter)
            //autoCompleteTextView?.setText("Kurti", false)
            val autoCompleteTextView = testCategoriesInputLayout.editText as? AutoCompleteTextView

            autoCompleteTextView?.setOnItemClickListener { parent, view, position, id ->
                val categoryName = parent.getItemAtPosition(position) as String
                onCategoryDropdownChange(categoryName)
            }
        }

        //viewModel.catalogues.observe(viewLifecycleOwner){ // With viewModels, this seems to be the correct way to do it instead of passing the list as parameter to the adapter
        //    backupAdapter.setCatalogues(it) // Because 1) the way data is retrieved from the ViewModel + Repository; don't have to go down the init route (i.e. where & when?) + it might be how things are done with API calls
        //}                                   // Here the lifeCycleChanges observing handles passing it to the adapter the first time as well. Thus, we don't have to explicitly pass it as parameter.
                                            // Even though at this point, we might not necessarily need to 'observe'. Because this data isn't modified by the user. But that wouldn't be all, data can get refreshed from API thus does need observation, and so this would be the way to go

        viewModel.pagingCatalogues.observe(viewLifecycleOwner) {
            Log.d("PFragment", "Observed?")
            //marketAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        viewModel.newCatalogues.observe(viewLifecycleOwner) {
            Log.d("NewCats", "Observed!")
            backupAdapterNew.submitList(it)
        }

        viewModel.categories.observe(viewLifecycleOwner) {
            val categories = arrayListOf("All")
            categories.addAll( it.map { cat -> cat.name } )
            binding.apply {
                val adapter = ArrayAdapter(requireContext(), R.layout.item_size_menu, categories)
                val autoCompleteTextView = testCategoriesInputLayout.editText as? AutoCompleteTextView
                autoCompleteTextView?.setAdapter(adapter)
            }
        }

        //viewModel.insert()

    }

    // Sharing to WhatsApp :- https://guides.codepath.com/android/Sharing-Content-with-Intents
    override fun onShareButtonClick(images: List<ImageView>) {
        // For creation of files
        for((index, image) in images.withIndex()) {
            val drawable = image.drawable

            val bmp = if(drawable is BitmapDrawable) {
                drawable.bitmap
            } else {
                null
            }

            context?.let {
                val file = File(it.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_$index.png")
                val out = FileOutputStream(file)
                bmp?.compress(Bitmap.CompressFormat.PNG, 90, out)
                out.close()
            }
        }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(catalogue: Catalogue) {
        Log.d("OnItemClick", "$catalogue")
        //viewModel.insert()
        //val action = MarketFragmentDirections.actionMarketFragmentToCatalogueFragment(catalogue)
        //findNavController().navigate(action)
    }

    override fun onItemClick(catalogue: CatalogueNew) {
        Log.d("OnItemClickNew", "$catalogue")
        val action = MarketFragmentDirections.actionMarketFragmentToCatalogueFragment(catalogue)
        findNavController().navigate(action)
    }

    private fun onCategoryDropdownChange(name: String) {
        Log.d("OnCategory", name)
        viewModel.onCategoryDropdownChange(name)
    }

}