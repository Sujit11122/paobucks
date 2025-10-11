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
            CoffeeItem("Espresso", "Strong black coffee", 2.50, R.drawable.ic_launcher_background),
            CoffeeItem("Cappuccino", "Coffee with milk foam", 3.00, R.drawable.ic_launcher_background),
            CoffeeItem("Latte", "Coffee with milk", 3.50, R.drawable.ic_launcher_background)
        )

        coffeeAdapter = CoffeeAdapter(coffeeList)
        recyclerView.adapter = coffeeAdapter
    }
}
