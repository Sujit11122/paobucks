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

        // Intent data
        val name = intent.getStringExtra("name") ?: getString(R.string.sample_coffee_name)
        val description = intent.getStringExtra("description") ?: getString(R.string.sample_coffee_description)
        val basePrice = intent.getDoubleExtra("price", 0.0)
        val imageRes = intent.getIntExtra("imageRes", R.drawable.ic_launcher_background)

        // UI references
        val imageView = findViewById<ImageView>(R.id.imageCoffeeDetail)
        val textName = findViewById<TextView>(R.id.textNameDetail)
        val textDescription = findViewById<TextView>(R.id.textDescriptionDetail)
        val textPrice = findViewById<TextView>(R.id.textPriceDetail)
        val favoriteBtn = findViewById<ImageButton>(R.id.buttonFavorite)
        val buttonAddOns = findViewById<Button>(R.id.buttonSelectAddOns)
        val buttonAddToCart = findViewById<Button>(R.id.buttonAddToCart)
        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)

        val radioSmall = findViewById<RadioButton>(R.id.radioSmall)
        val radioMedium = findViewById<RadioButton>(R.id.radioMedium)
        val radioLarge = findViewById<RadioButton>(R.id.radioLarge)

        // Set initial UI
        imageView.setImageResource(imageRes)
        textName.text = name
        textDescription.text = description
        updatePrice(basePrice, textPrice) // Initial price display

        // Favorite handling
        fun updateFavoriteIcon() {
            val coffee = CoffeeItem(name, description, basePrice, imageRes)
            if (FavoriteManager.isFavorite(coffee)) {
                favoriteBtn.setImageResource(R.drawable.ic_favorite_filled)
            } else {
                favoriteBtn.setImageResource(R.drawable.ic_favorite_border)
            }
        }

        updateFavoriteIcon()

        favoriteBtn.setOnClickListener {
            val coffee = CoffeeItem(name, description, basePrice, imageRes)
            if (FavoriteManager.isFavorite(coffee)) {
                FavoriteManager.removeFavorite(coffee)
                Toast.makeText(this, "$name removed from favorites", Toast.LENGTH_SHORT).show()
            } else {
                FavoriteManager.addFavorite(coffee)
                Toast.makeText(this, "$name added to favorites", Toast.LENGTH_SHORT).show()
            }
            updateFavoriteIcon()
        }

        // Add-ons button
        buttonAddOns.setOnClickListener { showAddOnDialog(basePrice, textPrice) }

        // Size change listener: update price dynamically
        val sizeChangeListener = { _: CompoundButton, _: Boolean -> updatePrice(basePrice, textPrice) }
        radioSmall.setOnCheckedChangeListener(sizeChangeListener)
        radioMedium.setOnCheckedChangeListener(sizeChangeListener)
        radioLarge.setOnCheckedChangeListener(sizeChangeListener)

        // Add to cart button
        buttonAddToCart.setOnClickListener {

            val (size, multiplier) = when {
                radioSmall.isChecked -> "Small" to 0.8
                radioLarge.isChecked -> "Large" to 1.2
                else -> "Medium" to 1.0
            }

            val coffee = CoffeeItem(
                name = name,
                description = description,
                basePrice = basePrice,
                imageRes = imageRes,
                selectedAddOns = selectedAddOns.toMutableList(),
                size = size,
                sizeMultiplier = multiplier
            )

            CartManager.addItem(coffee)

            Toast.makeText(
                this,
                getString(R.string.added_to_cart_message, name),
                Toast.LENGTH_SHORT
            ).show()

            finish()
        }

        // Back button
        buttonBack.setOnClickListener { finish() }
    }

    // Update the displayed price based on size and add-ons
    private fun updatePrice(basePrice: Double, priceTextView: TextView) {
        val multiplier = when {
            findViewById<RadioButton>(R.id.radioSmall).isChecked -> 0.8
            findViewById<RadioButton>(R.id.radioLarge).isChecked -> 1.2
            else -> 1.0
        }

        val addOnsTotal = selectedAddOns.sumOf { it.price }
        val finalPrice = basePrice * multiplier + addOnsTotal
        priceTextView.text = "$${"%.2f".format(finalPrice)}"
    }

    // Add-ons selection dialog
    private fun showAddOnDialog(basePrice: Double, priceTextView: TextView) {
        val addOnNames = AddOnProvider.availableAddOns.map { it.name }.toTypedArray()

        val checkedItems = BooleanArray(addOnNames.size) { index ->
            selectedAddOns.contains(AddOnProvider.availableAddOns[index])
        }

        AlertDialog.Builder(this)
            .setTitle("Select Add-Ons")
            .setMultiChoiceItems(addOnNames, checkedItems) { _, which, isChecked ->
                val addOn = AddOnProvider.availableAddOns[which]
                if (isChecked && !selectedAddOns.contains(addOn)) selectedAddOns.add(addOn)
                else selectedAddOns.remove(addOn)

                // Update price dynamically
                updatePrice(basePrice, priceTextView)
            }
            .setPositiveButton("Done", null)
            .show()
    }
}
