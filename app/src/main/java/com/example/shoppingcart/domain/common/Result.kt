package com.example.shoppingcart.domain.common


sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Loading<T>(val show: Boolean) : Result<T>()
    data class Failure<T>(val errorMessage: ResponseErrorUI?) : Result<T>()
}