package com.example.shoppingcart.domain.usesCases


import com.example.shoppingcart.domain.models.ProductsItem
import kotlinx.coroutines.flow.Flow
import com.example.shoppingcart.domain.common.Result
import com.example.shoppingcart.domain.models.Payment

interface ProductsUseCase {

    fun invokeProducts(): Flow<Result<List<ProductsItem>>>

    fun invokePayment(): Flow<Result<Payment>>
}