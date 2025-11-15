package com.example.paobucks

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoriteActivity : AppCompatActivity() {

    private lateinit var recyclerViewFavorites: RecyclerView
    private lateinit var emptyFavoritesLayout: LinearLayout
    private lateinit var favoriteAdapter: CoffeeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        val toolbar = findViewById<Toolbar>(R.id.toolbarFavorites)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        recyclerViewFavorites = findViewById(R.id.recyclerViewFavorites)
        emptyFavoritesLayout = findViewById(R.id.emptyFavoritesLayout)

        updateFavoritesUI()
    }

    private fun updateFavoritesUI() {
        val favoriteItems = FavoriteManager.getFavorites()

        if (favoriteItems.isEmpty()) {
            emptyFavoritesLayout.visibility = View.VISIBLE
            recyclerViewFavorites.visibility = View.GONE
        } else {
            emptyFavoritesLayout.visibility = View.GONE
            recyclerViewFavorites.visibility = View.VISIBLE

            recyclerViewFavorites.layoutManager = LinearLayoutManager(this)
            favoriteAdapter = CoffeeAdapter(
                favoriteItems,
                onAddToCartClick = { coffee ->
                    CartManager.addItem(coffee)
                },
                onItemClick = { coffee ->
                    val intent = Intent(this, CoffeeDetailActivity::class.java).apply {
                        putExtra("name", coffee.name)
                        putExtra("description", coffee.description)
                        putExtra("price", coffee.basePrice)
                        putExtra("imageRes", coffee.imageRes)
                    }
                    startActivity(intent)
                },
                onFavoriteClick = { coffee ->
                    if (FavoriteManager.isFavorite(coffee)) {
                        FavoriteManager.removeFavorite(coffee)
                    } else {
                        FavoriteManager.addFavorite(coffee)
                    }
                    updateFavoritesUI() // Refresh the list after remove
                }
            )
            recyclerViewFavorites.adapter = favoriteAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        updateFavoritesUI()
    }
}
