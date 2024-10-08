package ru.gb.android.marketsample.clean.data.product

import retrofit2.http.GET

interface ProductApiService {
    @GET("test_api_products.json")
    suspend fun getProducts(): List<ProductDto>
}
