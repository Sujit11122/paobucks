package com.example.paobucks

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class CoffeeAdapter(private val coffeeList: List<CoffeeItem>) :
    RecyclerView.Adapter<CoffeeAdapter.CoffeeViewHolder>() {

    class CoffeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageCoffee: ImageView = itemView.findViewById(R.id.imageCoffee)
        val textName: TextView = itemView.findViewById(R.id.textName)
        val textDescription: TextView = itemView.findViewById(R.id.textDescription)
        val textPrice: TextView = itemView.findViewById(R.id.textPrice)
        val buttonAdd: Button = itemView.findViewById(R.id.buttonAddToCart)
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

        Log.d("CoffeeAdapter", "Binding item: ${coffee.name}")

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, CoffeeDetailActivity::class.java)
            intent.putExtra("name", coffee.name)
            intent.putExtra("description", coffee.description)
            intent.putExtra("price", coffee.price)
            intent.putExtra("imageRes", coffee.imageRes)
            context.startActivity(intent)
        }

        holder.buttonAdd.setOnClickListener {
            Toast.makeText(holder.itemView.context, "${coffee.name} added to cart!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = coffeeList.size
}
