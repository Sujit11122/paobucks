package com.example.paobucks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class CoffeeAdapter(
    private val coffeeList: List<CoffeeItem>,
    private val onAddToCartClick: (CoffeeItem) -> Unit,
    private val onItemClick: (CoffeeItem) -> Unit,
    private val onFavoriteClick: (CoffeeItem) -> Unit
) : RecyclerView.Adapter<CoffeeAdapter.CoffeeViewHolder>() {

    inner class CoffeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.textName)
        val priceText: TextView = itemView.findViewById(R.id.textPrice)
        val addButton: Button = itemView.findViewById(R.id.buttonAddToCart)
        val imageView: ImageView = itemView.findViewById(R.id.imageCoffee)
        val favoriteBtn: ImageButton = itemView.findViewById(R.id.imageFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coffee, parent, false)
        return CoffeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoffeeViewHolder, position: Int) {
        val coffee = coffeeList[position]

        holder.nameText.text = coffee.name
        holder.priceText.text = "$%.2f".format(coffee.price)
        holder.imageView.setImageResource(coffee.imageRes)

        // Add to Cart
        holder.addButton.setOnClickListener { onAddToCartClick(coffee) }

        // Open Coffee Detail
        holder.itemView.setOnClickListener { onItemClick(coffee) }

        // Favorite toggle
        holder.favoriteBtn.setOnClickListener { onFavoriteClick(coffee) }

        // Update favorite icon
        if (FavoriteManager.isFavorite(coffee)) {
            holder.favoriteBtn.setImageResource(R.drawable.ic_favorite_filled)
        } else {
            holder.favoriteBtn.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    override fun getItemCount(): Int = coffeeList.size
}
