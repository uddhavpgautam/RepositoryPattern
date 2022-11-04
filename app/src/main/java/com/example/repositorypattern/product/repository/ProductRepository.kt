package com.example.repositorypattern.product.repository

import com.example.repositorypattern.product.model.Product
import com.example.repositorypattern.utils.Result
import io.reactivex.Single

abstract class ProductRepository {
    abstract fun getProducts(): Single<Result<List<Product>>>
    abstract fun getWishlist(): Single<Result<List<Product>>>
}
