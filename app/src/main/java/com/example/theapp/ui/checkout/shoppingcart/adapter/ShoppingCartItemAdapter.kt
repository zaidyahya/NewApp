package com.example.theapp.ui.checkout.shoppingcart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.theapp.databinding.ItemShoppingCartBinding
import com.example.theapp.model.OrderItem

class ShoppingCartItemAdapter(
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ShoppingCartItemAdapter.ShoppingCartItemViewHolder>() {

    private lateinit var orderItemsList: List<OrderItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartItemViewHolder {
        val binding = ItemShoppingCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingCartItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingCartItemViewHolder, position: Int) {
        val currentItem = orderItemsList[position]

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return orderItemsList.size
    }

    fun setOrderItems(orderItems: List<OrderItem>) {
        orderItemsList = orderItems
        notifyDataSetChanged()
    }

    inner class ShoppingCartItemViewHolder(private val binding: ItemShoppingCartBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            //binding.imageButtonEdit.setOnClickListener {
                //listener.onEditButtonClick()
            //}
        }

        fun bind(orderItem: OrderItem) {
            binding.apply {
                orderItem.product.image?.let {
                    imageViewProduct.setImageResource(it)
                }
                textViewSize.text = "Size: ${orderItem.size} | Qty: 5"
                textViewName.text = orderItem.product.name
                binding.imageButtonEdit.setOnClickListener {
                    listener.onEditButtonClick(orderItem)
                }
            }
        }

    }

    interface OnItemClickListener {
        fun onEditButtonClick(orderItem: OrderItem)
    }
}