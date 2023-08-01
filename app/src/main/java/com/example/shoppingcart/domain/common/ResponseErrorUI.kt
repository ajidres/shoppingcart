package com.example.shoppingcart.domain.common

data class ResponseErrorUI (
    var errorCode: String = "",
    var description: String = "",
    var message: String = "",
)