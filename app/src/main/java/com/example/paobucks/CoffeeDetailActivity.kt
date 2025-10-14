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

        // Get data from Intent
        val name = intent.getStringExtra("name") ?: "Coffee"
        val description = intent.getStringExtra("description") ?: "Description"
        val price = intent.getDoubleExtra("price", 0.0)
        val imageRes = intent.getIntExtra("imageRes", R.drawable.ic_launcher_background)

        // Bind views
        val imageCoffee = findViewById<ImageView>(R.id.imageCoffeeDetail)
        val textName = findViewById<TextView>(R.id.textNameDetail)
        val textDescription = findViewById<TextView>(R.id.textDescriptionDetail)
        val textPrice = findViewById<TextView>(R.id.textPriceDetail)
        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        val buttonAdd = findViewById<Button>(R.id.buttonAddToCartDetail)

        // Set data
        imageCoffee.setImageResource(imageRes)
        textName.text = name
        textDescription.text = description
        textPrice.text = "$$price"

        buttonBack.setOnClickListener {
            finish()
        }

        buttonAdd.setOnClickListener {
            Toast.makeText(this, "$name added to cart!", Toast.LENGTH_SHORT).show()
        }
    }
}
