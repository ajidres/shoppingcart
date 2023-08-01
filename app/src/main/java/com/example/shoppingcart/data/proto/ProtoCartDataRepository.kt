package com.example.shoppingcart.data.proto

import androidx.datastore.core.DataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.example.shoppingcart.CartProducts
import com.example.shoppingcart.ProductsItem
import javax.inject.Inject
import kotlinx.coroutines.flow.toList

class ProtoCartDataRepository @Inject constructor(
    private val dataStore: DataStore<CartProducts>
) : ProtoCartRepository {


    override suspend fun getCartData(): LiveData<MutableList<ProductsItem>> {
        return dataStore.data.asLiveData().switchMap { MutableLiveData(it.productsList) }
    }

    override suspend fun addCartData(data: MutableList<ProductsItem>) {
        dataStore.updateData { cartPreferences: CartProducts ->
            cartPreferences.toBuilder().addAllProducts(data).build()
        }
    }

    override suspend fun clearAllData() {
        dataStore.updateData { cartPreferences: CartProducts ->
            cartPreferences.toBuilder().clear().build()
        }
    }
}