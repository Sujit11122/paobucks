package com.example.paobucks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
            CoffeeItem("Flat White", "Velvety espresso with microfoam", 3.60, R.drawable.flatwhite),
        )

        coffeeAdapter = CoffeeAdapter(coffeeList)
        recyclerView.adapter = coffeeAdapter
    }
}
