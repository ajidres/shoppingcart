package com.example.shoppingcart.ui.car

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingcart.data.proto.ProtoCartDataRepository
import com.example.shoppingcart.ProductsItem
import com.example.shoppingcart.data.proto.ProtoCartRepository
import com.example.shoppingcart.domain.common.Result
import com.example.shoppingcart.domain.models.Payment
import com.example.shoppingcart.domain.usesCases.ProductsUseCase
import javax.inject.Inject
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class FragmentCarViewModel @Inject constructor(private val dataRepository: ProtoCartRepository,
                                               private val productUseCase: ProductsUseCase): ViewModel() {

    val originProducts = MutableLiveData<List<ProductsItem>>()

    val payment = MutableLiveData<Result<Payment>>()

    init {
        getAllRecentProducts()
    }

    private lateinit var _products: LiveData<MutableList<ProductsItem>>
    val products: LiveData<MutableList<ProductsItem>> = _products


    private fun getAllRecentProducts() = viewModelScope.launch {
        _products = dataRepository.getCartData()
    }

    fun addProducts(data:MutableList<ProductsItem>) = viewModelScope.launch {
        dataRepository.addCartData(data)
    }

    fun deleteAllCart() = viewModelScope.launch {
        dataRepository.clearAllData()
    }

    fun payment(){

        viewModelScope.launch {
            productUseCase.invokePayment().collect {
                payment.value = it
            }
        }

    }



}