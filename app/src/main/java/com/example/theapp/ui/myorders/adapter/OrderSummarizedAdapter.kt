package com.example.theapp.ui.myorders.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.theapp.databinding.ItemOrderSummarizedBinding
import com.example.theapp.model.OrderSummary

class OrderSummarizedAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<OrderSummary, OrderSummarizedAdapter.OrderSummaryViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderSummaryViewHolder {
        val binding = ItemOrderSummarizedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderSummaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderSummaryViewHolder, position: Int) {
        val currentItem = getItem(position)
        if(currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class OrderSummaryViewHolder(private val binding: ItemOrderSummarizedBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: OrderSummary) {
            binding.apply {
                textViewDate.text = order.datePlaced
                textViewOrderNumber.text = "Order #: ${order.id}"
                textViewShipToValue.text = order.customerName
                textViewStatusValue.text = order.status
                textViewItems.text = "Items: ${order.numItems}"
                when(order.status) { //https://mui.com/customization/palette/
                    "Completed" -> textViewStatusValue.setTextColor(Color.parseColor("#388e3c"))
                    "In Progress" -> textViewStatusValue.setTextColor(Color.parseColor("#f57c00"))
                    "Cancelled" -> textViewStatusValue.setTextColor(Color.parseColor("#d32f2f"))
                }

                root.setOnClickListener {
                    listener.onItemClick(order.id)
                }
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(id: String)
    }

    class DiffCallback : DiffUtil.ItemCallback<OrderSummary>() {
        override fun areItemsTheSame(oldItem: OrderSummary, newItem: OrderSummary): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: OrderSummary, newItem: OrderSummary): Boolean =
            oldItem == newItem
    }
}