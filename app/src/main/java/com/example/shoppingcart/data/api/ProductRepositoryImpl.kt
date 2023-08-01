package com.example.shoppingcart.data.api

import com.example.shoppingcart.domain.models.ProductsItem
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import com.example.shoppingcart.domain.common.Result
import com.example.shoppingcart.domain.models.Payment
import com.example.shoppingcart.util.bodyOrException
import com.example.shoppingcart.util.getErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class ProductRepositoryImpl  @Inject constructor(private val apis: EndPoints) : ProductsRepository{

    override fun products(): Flow<Result<List<ProductsItem>>> {
        return flow<Result<List<ProductsItem>>> {
            val response = apis.getProducts().bodyOrException()
            emit(Result.Success(response))
        }.onStart {
            emit(Result.Loading(true))
        }.onCompletion {
            emit(Result.Loading(false))
        }.catch {
            emit(Result.Failure(errorMessage = getErrorResponse(it)))
        }.flowOn(Dispatchers.IO)
    }

    override fun payment(): Flow<Result<Payment>> {
        return flow<Result<Payment>> {
            val response = apis.setPayment().bodyOrException()
            emit(Result.Success(response))
        }.onStart {
            emit(Result.Loading(true))
        }.onCompletion {
            emit(Result.Loading(false))
        }.catch {
            emit(Result.Failure(errorMessage = getErrorResponse(it)))
        }.flowOn(Dispatchers.IO)
    }


}