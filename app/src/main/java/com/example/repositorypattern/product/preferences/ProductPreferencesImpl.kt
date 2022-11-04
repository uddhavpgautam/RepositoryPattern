package com.example.repositorypattern.product.preferences

class ProductPreferencesImpl: ProductPreferences {
    override fun isFavourite(id: String?): Boolean {
        return true
    }

    override fun getFavourites(): List<String> {
        return listOf("")
    }
}