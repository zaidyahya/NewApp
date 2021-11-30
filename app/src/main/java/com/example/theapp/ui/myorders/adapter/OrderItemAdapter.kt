package com.example.theapp.ui.myorders.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.theapp.R
import com.example.theapp.databinding.ItemOrderItemBinding
import com.example.theapp.model.OrderItemWithDetails

class OrderItemAdapter : ListAdapter<OrderItemWithDetails, OrderItemAdapter.OrderItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        val binding = ItemOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class OrderItemViewHolder(private val binding: ItemOrderItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(orderItem: OrderItemWithDetails) {
            binding.apply {
                imageViewProduct.setImageResource(R.drawable.green_big)
                textViewName.text = orderItem.productName
                textViewSize.text = "Size: ${orderItem.sizeAbbreviation} | Qty: ${orderItem.quantity}"
                textViewPrice.text = "Rs. ${orderItem.productPrice}"
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<OrderItemWithDetails>() {
        override fun areItemsTheSame(oldItem: OrderItemWithDetails, newItem: OrderItemWithDetails): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: OrderItemWithDetails, newItem: OrderItemWithDetails): Boolean =
            oldItem == newItem
    }


}