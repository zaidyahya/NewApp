package com.example.theapp.ui.catalogue.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.theapp.databinding.ItemCatalogueBinding
import com.example.theapp.model.Product

class CatalogueItemAdapter(
    private val productList: List<Product>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<CatalogueItemAdapter.CatalogueItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogueItemViewHolder {
        val binding = ItemCatalogueBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CatalogueItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatalogueItemViewHolder, position: Int) {
        val currentItem = productList[position]

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class CatalogueItemViewHolder(private val binding: ItemCatalogueBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
            binding.buttonShareNow.setOnClickListener { listener.onShareNowButtonClick() }
        }

        fun bind(product: Product) {
            binding.apply {
                product.image?.let {
                    imageViewImage.setImageResource(it) // Views of the item_catalogue.xml, as we are ViewBinding that layout here
                }
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val product = productList[position]
                listener.onItemClick(product)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(product: Product)
        fun onShareNowButtonClick()
    }
}