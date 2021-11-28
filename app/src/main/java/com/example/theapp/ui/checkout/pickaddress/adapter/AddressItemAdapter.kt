package com.example.theapp.ui.checkout.pickaddress.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.theapp.databinding.ItemAddressBinding
import com.example.theapp.model.Customer

/**
 * ListAdapter because :-
 *  - It adds UI animations for add/edit/delete. So better accustomed for those RecyclerViews where modifications will happen
 *  - It does computation for same/changed objects in background thread
 */
class AddressItemAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<Customer, AddressItemAdapter.AddressItemViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressItemAdapter.AddressItemViewHolder {
        val binding = ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressItemAdapter.AddressItemViewHolder, position: Int) {
        val currentItem = getItem(position)

        if(currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class AddressItemViewHolder(private val binding: ItemAddressBinding) : RecyclerView.ViewHolder(binding.root),
    View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(customer: Customer) {
            val isSelected = customer.isSelected

            binding.apply {
                textViewName.text = customer.name
                buttonEdit.visibility = if(isSelected) View.VISIBLE else View.GONE
                buttonDeliverToAddress.visibility = if(isSelected) View.VISIBLE else View.GONE
                radioButton.isChecked = isSelected
                buttonEdit.setOnClickListener { listener.onEditButtonClick(customer) }
                buttonDeliverToAddress.setOnClickListener { listener.onDeliverButtonClick(customer) }
            }
        }

        // Method gets called when item gets clicked. Implemented due to View.OnClickListener implementation
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<Customer>() {
        override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem == newItem // Data class automatically implements equals method that compares the property of the model
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onEditButtonClick(customer: Customer)
        fun onDeliverButtonClick(customer: Customer)
    }

}