package com.example.theapp.ui.market.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.theapp.R
import com.example.theapp.databinding.ItemMarketBinding
import com.example.theapp.model.Catalogue

class MarketItemAdapter(
    private val listener: OnItemCLickListener
) :
    PagingDataAdapter<Catalogue, MarketItemAdapter.MarketItemViewHolder>(PHOTO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketItemViewHolder {
        val binding =
            ItemMarketBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MarketItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarketItemViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class MarketItemViewHolder(private val binding: ItemMarketBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(catalogue: Catalogue) {
            binding.apply {
                Glide.with(itemView)
                    .load(catalogue.products[0].imageUrl)
                    //.error(R.drawable.easypaisa_logo)
                    .into(imageViewImage)

                textViewName.text = catalogue.name

                buttonViewProduct.setOnClickListener {
                    listener.onItemClick(catalogue)
                }

            }
        }
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<Catalogue>() {
            override fun areItemsTheSame(oldItem: Catalogue, newItem: Catalogue) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Catalogue, newItem: Catalogue) =
                oldItem == newItem
        }
    }

    interface OnItemCLickListener {
        fun onItemClick(catalogue: Catalogue)
    }
}