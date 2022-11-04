package com.example.repositorypattern.product.model

// A cluster of DTOs to be mapped into a Product
data class DataProduct(
    val networkProduct: NetworkProduct,
    val isFavourite: Boolean
)