package com.example.theapp.ui.catalogue.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.theapp.R
import com.example.theapp.databinding.FragmentCatalogueBinding
import com.example.theapp.model.Product
import com.example.theapp.model.ProductNew
import com.example.theapp.ui.catalogue.adapter.CatalogueItemAdapter
import com.example.theapp.ui.catalogue.adapter.CatalogueItemAdapterNew

class CatalogueFragment : Fragment(R.layout.fragment_catalogue), CatalogueItemAdapter.OnItemClickListener,
    CatalogueItemAdapterNew.OnItemClickListener {

    private var _binding: FragmentCatalogueBinding? = null
    private val binding get() = _binding!!

    //private val productList = listOf( Product("Product 1", R.drawable.kurta_1), Product("Product 2", R.drawable.orange_small), Product("Product 3", R.drawable.green_big), Product("Product 4", R.drawable.kurta_1) )
    //private val catalogueAdapter = CatalogueItemAdapter(productList, this)
    //private lateinit var catalogueAdapter: CatalogueItemAdapter
    private lateinit var catalogueAdapterNew: CatalogueItemAdapterNew

    private val args by navArgs<CatalogueFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCatalogueBinding.bind(view)

        val catalogue = args.catalogue
        //catalogueAdapter = CatalogueItemAdapter(catalogue.products, this)
        catalogueAdapterNew = CatalogueItemAdapterNew(catalogue.products, this)

        binding.apply{

            recyclerViewCatalogue.apply {
                setHasFixedSize(true)
                //adapter = catalogueAdapter
                adapter = catalogueAdapterNew
            }

            buttonCopy.setOnClickListener {
                Log.d("HEY", "YAY!")
            }

            textViewHeader.text = catalogue.name
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(product: Product) {
        //val action = CatalogueFragmentDirections.actionCatalogueFragmentToProductFragment(product)
        //findNavController().navigate(action)
    }

    override fun onItemClick(product: ProductNew) {
        Log.d("CatalogueFragment", "onItemClick")
        val action = CatalogueFragmentDirections.actionCatalogueFragmentToProductFragment(product)
        findNavController().navigate(action)
    }

    override fun onShareNowButtonClick() {
        Log.d("CatalogueFragment", "onShareNowButtonClick")
    }
}