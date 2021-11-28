package com.example.theapp.ui.myearnings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.theapp.databinding.ItemPaymentHistoryBinding

class PaymentHistoryItemAdapter : RecyclerView.Adapter<PaymentHistoryItemAdapter.PaymentHistoryItemViewHolder>() {

    private val ordersList = listOf("15-01-2020", "30-01-2020", "15-02-2020", "30-02-2020", "15-06-2020", "30-06-2020", "15-08-2020", "30-08-2020", "15-09-2020", "30-10-2020")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentHistoryItemViewHolder {
        val binding = ItemPaymentHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PaymentHistoryItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PaymentHistoryItemViewHolder, position: Int) {
        val currentItem = ordersList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }

    class PaymentHistoryItemViewHolder(private val binding: ItemPaymentHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.textViewDate.text = item
        }
    }
}