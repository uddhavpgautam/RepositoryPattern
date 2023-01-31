package com.example.repositorypattern.product.repository

import com.example.repositorypattern.product.api.ProductApiService
import com.example.repositorypattern.product.model.DataProduct
import com.example.repositorypattern.product.model.NetworkProduct
import com.example.repositorypattern.product.model.Product
import com.example.repositorypattern.product.preferences.ProductPreferences
import com.example.repositorypattern.utils.Mapper
import com.example.repositorypattern.utils.Result
import io.reactivex.Single

class ProductRepositoryImpl(
    private val productApiService: ProductApiService,
    private val productDataMapper: Mapper<DataProduct, Product>,
    private val productPreferences: ProductPreferences
) : ProductRepository() {

    override fun getProducts(): Single<Result<List<Product>>> {
        return productApiService.getProducts().map {
            when (it) {
                is Result.Success -> Result.Success(mapProducts(it.value))
                is Result.Failure -> Result.Failure(it.throwable)
            }
        }
    }

    private fun mapProducts(networkProductList: List<NetworkProduct>): List<Product> {
        return networkProductList.map {
            productDataMapper.map(DataProduct(it, productPreferences.isFavourite(it.model)))
        }
    }

    override fun getWishlist(): Single<Result<List<Product>>> {
        return productApiService.getWishlist(productPreferences.getFavourites()).map {
            when (it) {
                is Result.Success -> Result.Success(mapWishlist(it.value))
                is Result.Failure -> Result.Failure(it.throwable)
            }
        }
    }

    private fun mapWishlist(wishlist: List<NetworkProduct>): List<Product> {
        return wishlist.map {
            productDataMapper.map(DataProduct(it, true))
        }
    }

}
