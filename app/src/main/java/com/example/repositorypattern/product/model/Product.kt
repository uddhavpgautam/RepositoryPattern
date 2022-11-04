package com.example.repositorypattern.product.model

// Entity
data class Product(
    val model: String,
    val name: String,
    val price: Price,
    val isFavourite: Boolean
) {
    // Value object
    data class Price(
        val nowPrice: Double,
        val wasPrice: Double
    ) {
        companion object {
            val EMPTY = Price(0.0, 0.0)
        }
    }
}