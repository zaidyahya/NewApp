package com.example.theapp.ui.myearnings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.theapp.databinding.ItemEarningBinding

class EarningItemAdapter : RecyclerView.Adapter<EarningItemAdapter.EarningItemViewHolder>() {

    private val ordersList = listOf("ZXADSASDADASD", "ASDOAJSDQWE", "PPOQWEPQWOPEPOQWEPO", "ZXADSASDADASD", "ASDOAJSDQWE", "PPOQWEPQWOPEPOQWEPO", "ZXADSASDADASD", "ASDOAJSDQWE", "PPOQWEPQWOPEPOQWEPO", "ZXADSASDADASD", "ASDOAJSDQWE", "PPOQWEPQWOPEPOQWEPO")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarningItemViewHolder {
        val binding = ItemEarningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EarningItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EarningItemViewHolder, position: Int) {
        val currentItem = ordersList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }

    class EarningItemViewHolder(private val binding: ItemEarningBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.textViewOrderNumberValue.text = item
        }
    }
}