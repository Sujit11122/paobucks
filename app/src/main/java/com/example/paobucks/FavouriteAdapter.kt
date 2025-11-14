package com.example.paobucks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FavoriteAdapter(
    private val favoriteItems: MutableList<CoffeeItem>,
    private val onRemoveFavorite: (CoffeeItem) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageCoffee: ImageView = itemView.findViewById(R.id.imageCoffeeCart)
        val nameText: TextView = itemView.findViewById(R.id.textCoffeeNameCart)
        val addOnsText: TextView = itemView.findViewById(R.id.textAddOnsCart)
        val priceText: TextView = itemView.findViewById(R.id.textPriceCart)
        val removeBtn: Button = itemView.findViewById(R.id.buttonRemoveCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val coffee = favoriteItems[position]
        holder.imageCoffee.setImageResource(coffee.imageRes)
        holder.nameText.text = coffee.name
        holder.addOnsText.text = if (coffee.selectedAddOns.isEmpty()) "Add-Ons: None"
        else "Add-Ons: ${coffee.selectedAddOns.joinToString { it.name }}"
        holder.priceText.text = "$%.2f".format(coffee.price)

        holder.removeBtn.text = "Remove"
        holder.removeBtn.setOnClickListener {
            onRemoveFavorite(coffee)
            favoriteItems.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, favoriteItems.size)
        }
    }

    override fun getItemCount(): Int = favoriteItems.size
}
