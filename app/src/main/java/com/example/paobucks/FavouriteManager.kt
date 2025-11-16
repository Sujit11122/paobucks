package com.example.paobucks

object FavoriteManager {
    private val favoriteItems = mutableListOf<CoffeeItem>()
    var onFavoriteChanged: ((coffee: CoffeeItem) -> Unit)? = null

    fun addFavorite(item: CoffeeItem) {
        if (!favoriteItems.any { it.id == item.id }) {
            favoriteItems.add(item)
            item.isFavorite = true
            onFavoriteChanged?.invoke(item)
        }
    }

    fun removeFavorite(item: CoffeeItem) {
        favoriteItems.removeAll { it.id == item.id }
        item.isFavorite = false
        onFavoriteChanged?.invoke(item)
    }

    fun isFavorite(item: CoffeeItem): Boolean = item.isFavorite

    fun getFavorites(): List<CoffeeItem> = favoriteItems
}
