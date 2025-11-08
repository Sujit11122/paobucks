package com.example.paobucks

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var textTotal: TextView
    private lateinit var emptyCartLayout: LinearLayout
    private lateinit var footerCart: LinearLayout
    private lateinit var payNowButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val toolbar = findViewById<Toolbar>(R.id.toolbarCart)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        recyclerView = findViewById(R.id.recyclerViewCart)
        textTotal = findViewById(R.id.textTotal)
        emptyCartLayout = findViewById(R.id.emptyCartLayout)
        footerCart = findViewById(R.id.footerCart)
        payNowButton = findViewById(R.id.buttonPayNow)

        payNowButton.setOnClickListener {
            Toast.makeText(this, "Payment feature coming soon!", Toast.LENGTH_SHORT).show()
        }

        updateCartUI()
    }

    override fun onResume() {
        super.onResume()
        updateCartUI()
    }

    private fun updateCartUI() {
        val cartItems = CartManager.getCartItems()

        if (cartItems.isEmpty()) {
            emptyCartLayout.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            footerCart.visibility = View.GONE
        } else {
            emptyCartLayout.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            footerCart.visibility = View.VISIBLE

            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = CoffeeAdapter(cartItems)

            val total = CartManager.getTotalPrice()
            textTotal.text = "Total: $%.2f".format(total)
        }
    }
}
