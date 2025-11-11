package com.example.paobucks

data class CoffeeItem(
    val name: String,
    val description: String,
    val price: Double,
    val imageRes: Int,
    var selectedAddOns: List<AddOn> = listOf()
)

data class AddOn(
    val name: String,
    val price: Double
)

