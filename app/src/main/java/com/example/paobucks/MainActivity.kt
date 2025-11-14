package com.example.paobucks

import android.content.Intent
import android.os.Bundle
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

        // Regular coffee list
        recyclerView = findViewById(R.id.recyclerViewCoffee)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // --- Pao Specials Section ---
        val recyclerViewSpecials = findViewById<RecyclerView>(R.id.recyclerViewSpecials)
        recyclerViewSpecials.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val specialList = listOf(
            CoffeeItem("Pao Latte Luxe", "Rich creamy blend with a Pao twist", 5.20, R.drawable.latte),
            CoffeeItem("Espaosion Shot", "Premium cappuccino with cinnamon dust", 6.50, R.drawable.cappuccino),
            CoffeeItem("Caramel Paoccino", "Espresso reinvented with caramel", 6.90, R.drawable.espresso)
        )

        val specialAdapter = CoffeeAdapter(
            specialList,
            onAddToCartClick = { coffee ->
                CartManager.addItem(coffee)
                Toast.makeText(
                    this,
                    getString(R.string.added_to_cart_message, coffee.name),
                    Toast.LENGTH_SHORT
                ).show()
            },
            onItemClick = { coffee ->
                val intent = Intent(this, CoffeeDetailActivity::class.java).apply {
                    putExtra("name", coffee.name)
                    putExtra("description", coffee.description)
                    putExtra("price", coffee.price)
                    putExtra("imageRes", coffee.imageRes)
                }
                startActivity(intent)
            },
            onFavoriteClick = { coffee ->
                if (FavoriteManager.isFavorite(coffee)) {
                    FavoriteManager.removeFavorite(coffee)
                    Toast.makeText(this, "${coffee.name} removed from favorites", Toast.LENGTH_SHORT).show()
                } else {
                    FavoriteManager.addFavorite(coffee)
                    Toast.makeText(this, "${coffee.name} added to favorites", Toast.LENGTH_SHORT).show()
                }
            }
        )

        recyclerViewSpecials.adapter = specialAdapter

        // --- Regular Coffee List ---
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
                Toast.makeText(
                    this,
                    getString(R.string.added_to_cart_message, coffee.name),
                    Toast.LENGTH_SHORT
                ).show()
            },
            onItemClick = { coffee ->
                val intent = Intent(this, CoffeeDetailActivity::class.java).apply {
                    putExtra("name", coffee.name)
                    putExtra("description", coffee.description)
                    putExtra("price", coffee.price)
                    putExtra("imageRes", coffee.imageRes)
                }
                startActivity(intent)
            },
            onFavoriteClick = { coffee ->
                if (FavoriteManager.isFavorite(coffee)) {
                    FavoriteManager.removeFavorite(coffee)
                    Toast.makeText(this, "${coffee.name} removed from favorites", Toast.LENGTH_SHORT).show()
                } else {
                    FavoriteManager.addFavorite(coffee)
                    Toast.makeText(this, "${coffee.name} added to favorites", Toast.LENGTH_SHORT).show()
                }
                coffeeAdapter.notifyDataSetChanged()
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
                R.id.nav_favorites -> {
                    startActivity(Intent(this, FavoriteActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
