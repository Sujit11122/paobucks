package com.example.paobucks

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paobucks.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var coffeeAdapter: CoffeeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewCoffee)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Sample coffee data
        val coffeeList = listOf(
            CoffeeItem("Espresso", "Strong black coffee", 2.50, R.drawable.espresso),
            CoffeeItem("Cappuccino", "Coffee with milk foam", 3.00, R.drawable.cappuccino),
            CoffeeItem("Latte", "Coffee with milk", 3.50, R.drawable.latte),
            CoffeeItem("Americano", "Espresso with hot water", 2.75, R.drawable.americano),
            CoffeeItem("Mocha", "Chocolate-flavored coffee", 3.80, R.drawable.mocha),
            CoffeeItem("Macchiato", "Espresso with milk foam on top", 3.20, R.drawable.macchiato),
            CoffeeItem("Flat White", "Velvety espresso with microfoam", 3.60, R.drawable.flatwhite)
        )

        // Bottom Navigation setup
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

        // RecyclerView Adapter
        coffeeAdapter = CoffeeAdapter(coffeeList)
        recyclerView.adapter = coffeeAdapter
    }
}
