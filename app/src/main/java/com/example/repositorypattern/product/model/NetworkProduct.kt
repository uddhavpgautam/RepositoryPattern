package com.example.repositorypattern.product.model

import com.google.gson.annotations.SerializedName

// Network DTO
data class NetworkProduct(
    @SerializedName("model")
    val model: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("nowPrice")
    val nowPrice: Double?,
    @SerializedName("wasPrice")
    val wasPrice: Double?
)