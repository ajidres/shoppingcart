package com.example.shoppingcart.domain.usesCases

import com.example.shoppingcart.data.api.ProductsRepository
import com.example.shoppingcart.domain.models.ProductsItem
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import com.example.shoppingcart.domain.common.Result
import com.example.shoppingcart.domain.models.Payment

class ProductsUseCaseImpl @Inject constructor(private val repository: ProductsRepository) :
    ProductsUseCase {

    override fun invokeProducts(): Flow<Result<List<ProductsItem>>> = repository.products()

    override fun invokePayment(): Flow<Result<Payment>> = repository.payment()


}