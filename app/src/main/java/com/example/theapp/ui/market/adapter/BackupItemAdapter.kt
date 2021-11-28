package com.example.theapp.ui.market.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.theapp.databinding.ItemMarketBinding
import com.example.theapp.model.Catalogue

class BackupItemAdapter(
    private val listener: OnItemCLickListener
) : RecyclerView.Adapter<BackupItemAdapter.MarketItemViewHolder>(){
    private lateinit var catalogueList: List<Catalogue>
    //private var catalogueList = listOf(Catalogue("Hey"), Catalogue("Gay"))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketItemViewHolder {
        val binding = ItemMarketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarketItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarketItemViewHolder, position: Int) {
        val currentItem = catalogueList[position]
        if(currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return catalogueList.size
    }

    fun setCatalogues(catalogues: List<Catalogue>) {
        catalogueList = catalogues
        notifyDataSetChanged()
    }

    inner class MarketItemViewHolder(private val binding: ItemMarketBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            //binding.root.setOnClickListener(this)
        }

        fun bind(catalogue: Catalogue) {
            binding.apply {
                textViewName.text = catalogue.name
                imageViewImage.setImageResource(catalogue.products[0].image!!)
                buttonViewProduct.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val catalogue = catalogueList[position]
                        listener.onItemClick(catalogue)
                    }
                }
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val catalogue = catalogueList[position]
                listener.onItemClick(catalogue)
            }
        }

    }

    interface OnItemCLickListener {
        fun onItemClick(catalogue: Catalogue)
    }
}