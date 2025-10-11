package com.example.paobucks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CoffeeAdapter(private val coffeeList: List<CoffeeItem>) :
    RecyclerView.Adapter<CoffeeAdapter.CoffeeViewHolder>() {

    // ViewHolder class holds references to each item's views
    class CoffeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageCoffee: ImageView = itemView.findViewById(R.id.imageCoffee)
        val textName: TextView = itemView.findViewById(R.id.textName)
        val textDescription: TextView = itemView.findViewById(R.id.textDescription)
        val textPrice: TextView = itemView.findViewById(R.id.textPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coffee, parent, false)
        return CoffeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoffeeViewHolder, position: Int) {
        val coffee = coffeeList[position]
        holder.imageCoffee.setImageResource(coffee.imageRes)
        holder.textName.text = coffee.name
        holder.textDescription.text = coffee.description
        holder.textPrice.text = "$${coffee.price}"
    }

    override fun getItemCount(): Int = coffeeList.size
}
