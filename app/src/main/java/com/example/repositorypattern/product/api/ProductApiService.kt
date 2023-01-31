package com.example.repositorypattern.product.api

import com.example.repositorypattern.product.model.NetworkProduct
import com.example.repositorypattern.utils.Result
import io.reactivex.Single

// A DataSource for the Remote DB
interface ProductApiService {
    fun getProducts(): Single<Result<List<NetworkProduct>>>
    fun getWishlist(productIds: List<String>): Single<Result<List<NetworkProduct>>>
}
