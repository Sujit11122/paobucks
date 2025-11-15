package com.example.paobucks

data class CoffeeItem(
    val name: String,
    val description: String,
    val basePrice: Double,
    val imageRes: Int,
    var selectedAddOns: MutableList<AddOn> = mutableListOf(),
    var size: String = "Medium",
    var sizeMultiplier: Double = 1.0
)
    {
    fun totalPrice(): Double {
        val addOnsTotal = selectedAddOns.sumOf { it.price }
        return basePrice * sizeMultiplier + addOnsTotal
    }
}

data class AddOn(
    val name: String,
    val price: Double
)