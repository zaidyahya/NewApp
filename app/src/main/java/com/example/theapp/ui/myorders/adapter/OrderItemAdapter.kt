package com.example.theapp.ui.myorders.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.theapp.R
import com.example.theapp.databinding.ItemOrderBinding
import com.example.theapp.model.Order
import com.example.theapp.model.OrderItem
import com.example.theapp.model.Product
import java.util.*

class OrderItemAdapter(
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder>() {

    val itemOne = OrderItem(UUID.randomUUID().toString(), Product("Product 3", R.drawable.green_big, ""),"S")
    private var ordersList = listOf(
        Order(listOf(itemOne), status="Delivered"), Order(listOf(itemOne), status="In Progress"),
        Order(listOf(itemOne), status="Cancelled"), Order(listOf(itemOne), status="In Progress"),
        Order(listOf(itemOne), status="Delivered"), Order(listOf(itemOne), status="In Progress"),
        Order(listOf(itemOne), status="Cancelled"), Order(listOf(itemOne), status="In Progress")
    )

    //private var ordersList = listOf("XASD23A", "BLDD2231", "OPXSAD21", "XASD23A", "BLDD2231", "OPXSAD21", "XASD23A", "BLDD2231", "OPXSAD21")

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderItemViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        val currentItem = ordersList[position]
        if(currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }

    inner class OrderItemViewHolder(private val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root),
    View.OnClickListener{

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(order: Order) {
            binding.textViewOrderNumber.text = "Order #: BADLO23X"
            binding.textViewStatusValue.text = order.status
            when(order.status) { //https://mui.com/customization/palette/
                "Delivered" -> binding.textViewStatusValue.setTextColor(Color.parseColor("#388e3c"))
                "In Progress" -> binding.textViewStatusValue.setTextColor(Color.parseColor("#f57c00"))
                "Cancelled" -> binding.textViewStatusValue.setTextColor(Color.parseColor("#d32f2f"))
            }
        }

        override fun onClick(v: View?) {
            listener.onItemClick()
        }
    }

    interface OnItemClickListener {
        fun onItemClick()
    }
}