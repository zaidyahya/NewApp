package com.example.theapp.ui.checkout.pickaddress.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.theapp.databinding.ItemAddressBinding
import com.example.theapp.model.CustomerNew

class AddressItemAdapterNew(
    private val listener: OnItemClickListener
) : ListAdapter<CustomerNew, AddressItemAdapterNew.AddressItemViewHolder>(DiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressItemViewHolder {
        val binding = ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }


    inner class AddressItemViewHolder(private val binding: ItemAddressBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
//            binding.apply {
//                root.setOnClickListener {
//                    val position = bindingAdapterPosition
//                    if(position != RecyclerView.NO_POSITION) {
//                        listener.onItemClick(position)
//                    }
//                }
//            }
        }

        fun bind(customer: CustomerNew) {
            val isSelected = customer.isSelected
            binding.apply {
                textViewName.text = customer.name
                textViewPhoneNumber.text = customer.phoneNumber
                textViewAddress.text = "${customer.addressLine1} \n${customer.addressLine2} \n${customer.city}, Pakistan"

                radioButton.isChecked = isSelected
                buttonEdit.visibility = if(isSelected) View.VISIBLE else View.GONE
                buttonDeliverToAddress.visibility = if(isSelected) View.VISIBLE else View.GONE

                root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if(position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(customer.id, position)
                    }
                }

                buttonEdit.setOnClickListener { listener.onEditButtonClick(customer) }

                buttonDeliverToAddress.setOnClickListener { listener.onDeliverButtonClick(customer) }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(id: String, position: Int)
        fun onEditButtonClick(customer: CustomerNew)
        fun onDeliverButtonClick(customer: CustomerNew)
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