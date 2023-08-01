package com.example.shoppingcart.data.api


import com.example.shoppingcart.domain.models.Payment
import com.example.shoppingcart.domain.models.ProductsItem
import retrofit2.Response
import retrofit2.http.GET


interface EndPoints {

    @GET("SRVProductos")
    suspend fun getProducts(): Response<List<ProductsItem>>

    @GET("Pagar")
    suspend fun setPayment(): Response<Payment>

}
