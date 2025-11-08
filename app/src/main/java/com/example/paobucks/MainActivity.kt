package com.example.paobucks

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var coffeeAdapter: CoffeeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewCoffee)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val coffeeList = listOf(
            CoffeeItem("Espresso", "Strong black coffee", 2.50, R.drawable.espresso),
            CoffeeItem("Cappuccino", "Coffee with milk foam", 3.00, R.drawable.cappuccino),
            CoffeeItem("Latte", "Coffee with milk", 3.50, R.drawable.latte),
            CoffeeItem("Americano", "Espresso with hot water", 2.75, R.drawable.americano),
            CoffeeItem("Mocha", "Chocolate-flavored coffee", 3.80, R.drawable.mocha),
            CoffeeItem("Macchiato", "Espresso with milk foam on top", 3.20, R.drawable.macchiato),
            CoffeeItem("Flat White", "Velvety espresso with microfoam", 3.60, R.drawable.flatwhite)
        )

        coffeeAdapter = CoffeeAdapter(
            coffeeList,
            onAddToCartClick = { coffee ->
                CartManager.addItem(coffee)
                Toast.makeText(this, getString(R.string.added_to_cart_message, coffee.name), Toast.LENGTH_SHORT).show()
            },
            onItemClick = { coffee ->
                val intent = Intent(this, CoffeeDetailActivity::class.java).apply {
                    putExtra("name", coffee.name)
                    putExtra("description", coffee.description)
                    putExtra("price", coffee.price)
                    putExtra("imageRes", coffee.imageRes)
                }
                startActivity(intent)
            }
        )
        recyclerView.adapter = coffeeAdapter


        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    // CoffeeAdapter
    class CoffeeAdapter(
        private val coffeeList: List<CoffeeItem>,
        private val onAddToCartClick: (CoffeeItem) -> Unit,
        private val onItemClick: (CoffeeItem) -> Unit
    ) : RecyclerView.Adapter<CoffeeAdapter.CoffeeViewHolder>() {

        inner class CoffeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nameText: TextView = itemView.findViewById(R.id.textName)
            val priceText: TextView = itemView.findViewById(R.id.textPrice)
            val addButton: Button = itemView.findViewById(R.id.buttonAddToCart)
            val imageView: ImageView = itemView.findViewById(R.id.imageCoffee)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_coffee, parent, false)
            return CoffeeViewHolder(view)
        }

        override fun onBindViewHolder(holder: CoffeeViewHolder, position: Int) {
            val coffee = coffeeList[position]
            holder.nameText.text = coffee.name
            holder.priceText.text = holder.priceText.context.getString(R.string.coffee_price_placeholder_format, coffee.price)
            holder.imageView.setImageResource(coffee.imageRes)
            holder.addButton.setOnClickListener {
                onAddToCartClick(coffee)
            }
            holder.itemView.setOnClickListener {
                onItemClick(coffee)
            }
        }

        override fun getItemCount(): Int = coffeeList.size
    }
}
