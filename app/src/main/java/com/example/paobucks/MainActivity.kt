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
    private lateinit var recyclerViewSpecials: RecyclerView
    private lateinit var specialAdapter: CoffeeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // --- Special coffees
        val specialList = listOf(
            CoffeeItem("Pao Latte Luxe", "A luxurious creamy latte with a delicate Pao twist, perfectly balanced espresso and velvety milk, topped with a hint of caramel sweetness.", 5.20, R.drawable.paolatteluxe, id = 101),
            CoffeeItem("Espaosion Shot", "A bold, aromatic cappuccino with a burst of cinnamon dust and rich espresso layers — your perfect energizing kick for the day.", 6.50, R.drawable.caramelpaoccino, id = 102),
            CoffeeItem("Caramel Paoccino", "Decadent espresso reinvented with luscious caramel, frothy milk, and a hint of chocolate — a sweet treat in every sip.", 6.90, R.drawable.espaosionshot, id = 103)
        )

        // --- Home coffee list
        val coffeeList = listOf(
            CoffeeItem("Espresso", "Classic, strong, and rich — pure espresso to awaken your senses with every shot.", 2.50, R.drawable.espresso),
            CoffeeItem("Cappuccino", "Creamy milk foam layered over a robust coffee base for a classic and comforting cup.", 3.00, R.drawable.cappuccino),
            CoffeeItem("Latte", "Soft, smooth milk blends with rich coffee to create a warm and soothing drink.", 3.50, R.drawable.latte),
            CoffeeItem("Americano", "Light and smooth coffee made by diluting rich coffee with hot water for a gentle kick.", 2.75, R.drawable.americano),
            CoffeeItem("Mocha", "A chocolatey delight with creamy milk, perfect for those who crave a sweet coffee treat.", 3.80, R.drawable.mocha),
            CoffeeItem("Macchiato", "Strong coffee marked with a dash of milk foam for a bold yet smooth experience.", 3.20, R.drawable.macchiato),
            CoffeeItem("Flat White", "Velvety microfoam over a rich coffee base, creating a smooth and creamy cup with a silky finish.", 3.60, R.drawable.flatwhite)
        )

        // --- Home RecyclerView
        recyclerView = findViewById(R.id.recyclerViewCoffee)
        recyclerView.layoutManager = LinearLayoutManager(this)
        coffeeAdapter = CoffeeAdapter(
            coffeeList,
            onAddToCartClick = { coffee ->
                CartManager.addItem(coffee)
                Toast.makeText(this, "${coffee.name} added to cart", Toast.LENGTH_SHORT).show()
            },
            onItemClick = { coffee ->
                val intent = Intent(this, CoffeeDetailActivity::class.java).apply {
                    putExtra("name", coffee.name)
                    putExtra("description", coffee.description)
                    putExtra("price", coffee.basePrice)
                    putExtra("imageRes", coffee.imageRes)
                }
                startActivity(intent)
            },
            onFavoriteClick = { coffee ->
                if (coffee.isFavorite) FavoriteManager.removeFavorite(coffee)
                else FavoriteManager.addFavorite(coffee)
            }
        )
        recyclerView.adapter = coffeeAdapter

        // --- Specials RecyclerView
        recyclerViewSpecials = findViewById(R.id.recyclerViewSpecials)
        recyclerViewSpecials.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        specialAdapter = CoffeeAdapter(
            specialList,
            onAddToCartClick = { coffee ->
                CartManager.addItem(coffee)
                Toast.makeText(this, "${coffee.name} added to cart", Toast.LENGTH_SHORT).show()
            },
            onItemClick = { coffee ->
                val intent = Intent(this, CoffeeDetailActivity::class.java).apply {
                    putExtra("name", coffee.name)
                    putExtra("description", coffee.description)
                    putExtra("price", coffee.basePrice)
                    putExtra("imageRes", coffee.imageRes)
                }
                startActivity(intent)
            },
            onFavoriteClick = { coffee ->
                if (coffee.isFavorite) FavoriteManager.removeFavorite(coffee)
                else FavoriteManager.addFavorite(coffee)
            }
        )
        recyclerViewSpecials.adapter = specialAdapter

        // --- Subscribe to item-specific favorite changes
        FavoriteManager.onFavoriteChanged = { coffee ->
            val homeIndex = coffeeList.indexOf(coffee)
            if (homeIndex != -1) coffeeAdapter.notifyItemChanged(homeIndex)

            val specialIndex = specialList.indexOf(coffee)
            if (specialIndex != -1) specialAdapter.notifyItemChanged(specialIndex)
        }

        // --- Bottom navigation
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
