package com.example.repositorypattern.product.preferences

// A DataSource for the SharedPreferences
interface ProductPreferences {
    fun isFavourite(id: String?): Boolean
    fun getFavourites(): List<String>
}
