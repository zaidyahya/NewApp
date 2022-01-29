package com.example.theapp.ui.catalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.theapp.R
import com.example.theapp.databinding.ItemCatalogueBinding
import com.example.theapp.model.ProductNew

class CatalogueItemAdapterNew(
    private val productList: List<ProductNew>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<CatalogueItemAdapterNew.CatalogueItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogueItemViewHolder {
        val binding = ItemCatalogueBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CatalogueItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatalogueItemViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class CatalogueItemViewHolder(private val binding: ItemCatalogueBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if(position != RecyclerView.NO_POSITION) {
                        val product = productList[position]
                        listener.onItemClick(product)
                    }
                }

                buttonShareNow.setOnClickListener {
                    listener.onShareNowButtonClick(listOf(imageViewImage))
                }
            }
        }


        fun bind(product: ProductNew) {
            binding.apply {
                textViewName.text = product.name
                textViewPrice.text = "Rs. ${product.price}"
                imageViewImage.setImageResource(R.drawable.orange_small)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(product: ProductNew)
        fun onShareNowButtonClick(images: List<ImageView>)
    }
}