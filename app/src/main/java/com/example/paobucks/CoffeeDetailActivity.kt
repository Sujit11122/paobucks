package com.example.paobucks

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CoffeeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coffee_detail)


        val name = intent.getStringExtra("name") ?: getString(R.string.sample_coffee_name)
        val description = intent.getStringExtra("description") ?: getString(R.string.sample_coffee_description)
        val price = intent.getDoubleExtra("price", 0.0)
        val imageRes = intent.getIntExtra("imageRes", R.drawable.ic_launcher_background)


        findViewById<ImageView>(R.id.imageCoffeeDetail).setImageResource(imageRes)
        findViewById<TextView>(R.id.textNameDetail).text = name
        findViewById<TextView>(R.id.textDescriptionDetail).text = description
        findViewById<TextView>(R.id.textPriceDetail).text = getString(R.string.coffee_price_placeholder_format, price)

        val backBtn = findViewById<ImageButton>(R.id.buttonBack)
        backBtn.setOnClickListener { finish() }


        val addToCartBtn = findViewById<Button>(R.id.buttonAddToCart)
        addToCartBtn.setOnClickListener {
            val coffee = CoffeeItem(name, description, price, imageRes)
            CartManager.addItem(coffee)
            Toast.makeText(this, getString(R.string.added_to_cart_message, name), Toast.LENGTH_SHORT).show()
        }
    }
}
