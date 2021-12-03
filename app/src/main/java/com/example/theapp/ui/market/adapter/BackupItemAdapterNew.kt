package com.example.theapp.ui.market.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.theapp.R
import com.example.theapp.databinding.ItemMarketBinding
import com.example.theapp.model.CatalogueNew

class BackupItemAdapterNew(
    private val listener: OnItemClickListener
) : ListAdapter<CatalogueNew, BackupItemAdapterNew.MarketItemViewHolder>(DiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketItemViewHolder {
        val binding = ItemMarketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarketItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarketItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class MarketItemViewHolder(private val binding: ItemMarketBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                buttonViewProduct.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val catalogue = getItem(position)
                        listener.onItemClick(catalogue)
                    }
                }
            }
        }

        fun bind(catalogue: CatalogueNew) {
            binding.apply {
                imageViewImageOne.setImageResource(R.drawable.green_big)
                imageViewImageTwo.setImageResource(R.drawable.kurta_1)
                imageViewImageThree.setImageResource(R.drawable.orange_small)
                textViewName.text = catalogue.name

                buttonShareNow.setOnClickListener {
                    listener.onShareButtonClick(listOf(imageViewImageOne, imageViewImageTwo, imageViewImageThree))
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(catalogue: CatalogueNew)
        fun onShareButtonClick(images: List<ImageView>)
    }

    class DiffCallback: DiffUtil.ItemCallback<CatalogueNew>() {
        override fun areItemsTheSame(oldItem: CatalogueNew, newItem: CatalogueNew): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CatalogueNew, newItem: CatalogueNew): Boolean =
            oldItem == newItem
    }
}