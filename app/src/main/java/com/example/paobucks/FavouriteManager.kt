package com.example.paobucks

object FavoriteManager {
    private val favoriteItems = mutableListOf<CoffeeItem>()

    fun addFavorite(item: CoffeeItem) {
        if (!favoriteItems.any { it.name == item.name }) {
            favoriteItems.add(item)
        }
    }

    fun removeFavorite(item: CoffeeItem) {
        favoriteItems.removeAll { it.name == item.name }
    }

    fun isFavorite(item: CoffeeItem): Boolean {
        return favoriteItems.any { it.name == item.name }
    }

    fun getFavorites(): List<CoffeeItem> = favoriteItems

    fun isEmpty(): Boolean = favoriteItems.isEmpty()
}
