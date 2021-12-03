package com.example.theapp.ui.mycustomers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.theapp.databinding.ItemCustomerBinding
import com.example.theapp.model.CustomerNew

class CustomerItemAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<CustomerNew, CustomerItemAdapter.CustomerItemViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerItemViewHolder {
        val binding = ItemCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomerItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class CustomerItemViewHolder(private val binding: ItemCustomerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(customer: CustomerNew) {
            binding.apply {
                textViewName.text = customer.name
                textViewPhoneNumber.text = customer.phoneNumber
                textViewAddress.text = "${customer.addressLine1} \n${customer.addressLine2} \n${customer.city}, Pakistan"

                buttonEdit.setOnClickListener { listener.onEditButtonClick(customer) }
            }

        }
    }

    interface OnItemClickListener {
        fun onEditButtonClick(customer: CustomerNew)
    }

    class DiffCallback : DiffUtil.ItemCallback<CustomerNew>() {
        override fun areItemsTheSame(oldItem: CustomerNew, newItem: CustomerNew): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CustomerNew, newItem: CustomerNew): Boolean {
            return oldItem == newItem
        }
    }

}