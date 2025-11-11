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
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // Toolbar setup
        val toolbar = findViewById<Toolbar>(R.id.toolbarCart)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        // View references
        recyclerView = findViewById(R.id.recyclerViewCart)
        textTotal = findViewById(R.id.textTotal)
        emptyCartLayout = findViewById(R.id.emptyCartLayout)
        footerCart = findViewById(R.id.footerCart)
        payNowButton = findViewById(R.id.buttonPayNow)

        // Setup RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Load initial data
        updateCartUI()

        // Pay Now button
        payNowButton.setOnClickListener {
            Toast.makeText(this, getString(R.string.payment_coming_soon), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateCartUI() {
        val cartItems = CartManager.getCartItems().toMutableList()

        if (cartItems.isEmpty()) {
            // Empty cart view
            emptyCartLayout.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            footerCart.visibility = View.GONE
        } else {
            // Show cart items
            emptyCartLayout.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            footerCart.visibility = View.VISIBLE

            cartAdapter = CartAdapter(cartItems) {
                // Callback when cart changes
                updateCartUI()
            }

            recyclerView.adapter = cartAdapter

            val total = CartManager.getTotalPrice()
            textTotal.text = getString(R.string.total_price_format, total)
        }
    }
}
