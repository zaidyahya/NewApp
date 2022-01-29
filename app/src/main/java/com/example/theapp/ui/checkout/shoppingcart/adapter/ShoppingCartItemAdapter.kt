package com.example.theapp.ui.checkout.shoppingcart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.theapp.R
import com.example.theapp.databinding.ItemShoppingCartBinding
import com.example.theapp.model.ShoppingCartItemWithDetails

class ShoppingCartItemAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<ShoppingCartItemWithDetails, ShoppingCartItemAdapter.ShoppingCartItemViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartItemViewHolder {
        val binding = ItemShoppingCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingCartItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingCartItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }


    inner class ShoppingCartItemViewHolder(private val binding: ItemShoppingCartBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                imageButtonEdit.setOnClickListener {
                    val position = bindingAdapterPosition
                    if(position != RecyclerView.NO_POSITION) {
                        val cartItem = getItem(position)
                        listener.onEditButtonClick(cartItem)
                    }
                }
            }
        }

        fun bind(cartItem: ShoppingCartItemWithDetails) {
            binding.apply {
                imageViewProduct.setImageResource(R.drawable.green_big)
                textViewName.text = cartItem.productName
                textViewSize.text = "Size: ${cartItem.sizeAbbreviation} | Qty: ${cartItem.quantity}"
                textViewPrice.text = "Rs. ${cartItem.productPrice}"
            }
        }
    }

    interface OnItemClickListener {
        fun onEditButtonClick(cartItem: ShoppingCartItemWithDetails)
    }

    class DiffCallback: DiffUtil.ItemCallback<ShoppingCartItemWithDetails>() {
        override fun areItemsTheSame(
            oldItem: ShoppingCartItemWithDetails,
            newItem: ShoppingCartItemWithDetails
        ): Boolean =
            oldItem.id == newItem.id


        override fun areContentsTheSame(
            oldItem: ShoppingCartItemWithDetails,
            newItem: ShoppingCartItemWithDetails
        ): Boolean =
            oldItem == newItem
    }

}