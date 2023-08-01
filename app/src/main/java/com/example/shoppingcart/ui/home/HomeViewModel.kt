package com.example.shoppingcart.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingcart.domain.models.ProductsItem
import com.example.shoppingcart.domain.usesCases.ProductsUseCase
import javax.inject.Inject
import kotlinx.coroutines.launch
import com.example.shoppingcart.domain.common.Result
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class HomeViewModel @Inject constructor(private val productUseCase: ProductsUseCase): ViewModel() {

    val products = MutableLiveData<Result<List<ProductsItem>>>()
    val originProducts = MutableLiveData<List<ProductsItem>>()

    fun getProductsList(){

        viewModelScope.launch {
            productUseCase.invokeProducts().collect {
                products.value = it
            }
        }

    }

    fun filterData(data:String): List<ProductsItem> {
        val original =  originProducts.value
        val filter = original!!.filter {
            it.nombre.uppercase().contains(data.uppercase())
        }
        return filter
    }

}