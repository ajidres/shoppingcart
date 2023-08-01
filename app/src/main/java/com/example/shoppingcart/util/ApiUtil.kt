package com.example.shoppingcart.util


import com.example.shoppingcart.domain.common.ResponseErrorUI
import java.io.IOException
import retrofit2.HttpException
import retrofit2.Response

const val IO_EXCEPTION_CODE = -100
const val UNEXPECTED_ERROR_CODE = -101

fun <T : Any> Response<T>.bodyOrException(): T {
    val body = body()
    if (body != null && isSuccessful) {
        return body
    } else {
        throw HttpException(this)
    }
}

fun getErrorResponse(throwable: Throwable): ResponseErrorUI {

    val error = when (throwable) {
        is HttpException -> {
            throwable.code()
        }

        is IOException -> {
            IO_EXCEPTION_CODE
        }

        else -> {
            UNEXPECTED_ERROR_CODE
        }
    }

    return ResponseErrorUI(
        errorCode = error.toString(), description = throwable.message ?: ""
    )

}
