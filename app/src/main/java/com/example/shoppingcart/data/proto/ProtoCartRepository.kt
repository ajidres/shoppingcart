package com.example.shoppingcart.data.proto

import androidx.lifecycle.LiveData
import com.example.shoppingcart.ProductsItem

interface ProtoCartRepository {


    suspend fun getCartData(): LiveData<MutableList<ProductsItem>>
    suspend fun addCartData(data: MutableList<ProductsItem>)

    suspend fun clearAllData()



}