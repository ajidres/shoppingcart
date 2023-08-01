package com.example.shoppingcart.data.api


import com.example.shoppingcart.domain.models.ProductsItem
import com.example.shoppingcart.domain.common.Result
import com.example.shoppingcart.domain.models.Payment
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    fun products(): Flow<Result<List<ProductsItem>>>

    fun payment(): Flow<Result<Payment>>

}