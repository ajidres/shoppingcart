package com.example.shoppingcart.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Payment(
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String,

)