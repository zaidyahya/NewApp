package com.example.theapp.ui.myearnings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.theapp.databinding.ItemEarningBinding
import com.example.theapp.model.OrderSummary

class EarningItemAdapter : ListAdapter<OrderSummary, EarningItemAdapter.EarningItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarningItemViewHolder {
        val binding = ItemEarningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EarningItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EarningItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class EarningItemViewHolder(private val binding: ItemEarningBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: OrderSummary) {
            binding.apply {
                textViewDate.text = order.datePlaced
                textViewOrderNumberValue.text = order.id
                textViewAmount.text = "Rs. ${order.margin}"
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<OrderSummary>() {
        override fun areItemsTheSame(oldItem: OrderSummary, newItem: OrderSummary): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: OrderSummary, newItem: OrderSummary): Boolean =
            oldItem == newItem
    }

}