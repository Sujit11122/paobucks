package com.example.paobucks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private val cartItems: MutableList<CoffeeItem>,
    private val onCartChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageCoffee: ImageView = itemView.findViewById(R.id.imageCoffeeCart)
        val nameText: TextView = itemView.findViewById(R.id.textCoffeeNameCart)
        val addOnsText: TextView = itemView.findViewById(R.id.textAddOnsCart)
        val priceText: TextView = itemView.findViewById(R.id.textPriceCart)
        val removeBtn: Button = itemView.findViewById(R.id.buttonRemoveCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val coffee = cartItems[position]
        holder.imageCoffee.setImageResource(coffee.imageRes)
        holder.nameText.text = coffee.name
        holder.addOnsText.text = "Add-Ons: " + if (coffee.selectedAddOns.isEmpty()) "None"
        else coffee.selectedAddOns.joinToString { it.name }
        val totalPrice = coffee.price + coffee.selectedAddOns.sumOf { it.price }
        holder.priceText.text = "$%.2f".format(totalPrice)

        holder.removeBtn.setOnClickListener {
            CartManager.removeItem(coffee)
            cartItems.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, cartItems.size)
            onCartChanged()
        }
    }

    override fun getItemCount(): Int = cartItems.size
}
