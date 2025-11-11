package com.example.paobucks

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class CoffeeDetailActivity : AppCompatActivity() {

    private val selectedAddOns = mutableListOf<AddOn>()

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
        findViewById<TextView>(R.id.textPriceDetail).text =
            getString(R.string.coffee_price_placeholder_format, price)

        findViewById<ImageButton>(R.id.buttonBack).setOnClickListener { finish() }

        findViewById<Button>(R.id.buttonSelectAddOns).setOnClickListener {
            showAddOnDialog()
        }

        findViewById<Button>(R.id.buttonAddToCart).setOnClickListener {
            val coffee = CoffeeItem(
                name = name,
                description = description,
                price = price,
                imageRes = imageRes,
                selectedAddOns = selectedAddOns.toList()
            )
            CartManager.addItem(coffee)
            Toast.makeText(this, getString(R.string.added_to_cart_message, name), Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun showAddOnDialog() {
        val addOnNames = AddOnProvider.availableAddOns.map { it.name }.toTypedArray()
        val checkedItems = BooleanArray(addOnNames.size) { index ->
            selectedAddOns.contains(AddOnProvider.availableAddOns[index])
        }

        AlertDialog.Builder(this)
            .setTitle("Select Add-Ons")
            .setMultiChoiceItems(addOnNames, checkedItems) { _, which, isChecked ->
                val addOn = AddOnProvider.availableAddOns[which]
                if (isChecked) selectedAddOns.add(addOn) else selectedAddOns.remove(addOn)
            }
            .setPositiveButton("Done", null)
            .show()
    }
}
