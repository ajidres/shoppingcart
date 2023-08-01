package com.example.shoppingcart.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductsItem(
    @SerialName("id")
    val id: Int,
    @SerialName("imagen")
    val imagen: String,
    @SerialName("nombre")
    val nombre: String,
    @SerialName("precio")
    val precio: Double,
    @SerialName("descripcion")
    val descripcion: String,

    var quantity: Int,
)