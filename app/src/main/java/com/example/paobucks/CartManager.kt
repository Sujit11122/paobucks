package com.example.paobucks

object CartManager {
    private val cartItems = mutableListOf<CoffeeItem>()

    fun addItem(item: CoffeeItem) {
        cartItems.add(item)
    }

    fun getCartItems(): List<CoffeeItem> = cartItems

    fun getTotalPrice(): Double {
        return cartItems.sumOf { it.price + it.selectedAddOns.sumOf { addon -> addon.price } }
    }

    fun removeItem(item: CoffeeItem) {
        cartItems.remove(item)
    }

    fun isEmpty(): Boolean = cartItems.isEmpty()
}
