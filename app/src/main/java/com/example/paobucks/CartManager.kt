package com.example.paobucks

object CartManager {

    private val cartItems = mutableListOf<CoffeeItem>()

    fun addItem(item: CoffeeItem) {
        cartItems.add(item)
    }

    fun removeItem(item: CoffeeItem) {
        cartItems.remove(item)
    }

    fun getCartItems(): List<CoffeeItem> = cartItems.toList() // return immutable copy

    fun isEmpty(): Boolean = cartItems.isEmpty()

    fun clearCart() {
        cartItems.clear()
    }

    // Total price of all items in cart including add-ons and size
    fun getTotalPrice(): Double {
        return cartItems.sumOf { it.totalPrice() }
    }

    fun getTotalPriceFormatted(): String {
        return "%.2f".format(getTotalPrice())
    }
}
