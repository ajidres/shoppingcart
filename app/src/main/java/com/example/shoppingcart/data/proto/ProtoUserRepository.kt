package com.example.shoppingcart.data.proto

import androidx.lifecycle.LiveData
import com.example.shoppingcart.LoginPreferences

interface ProtoUserRepository {


    suspend fun getLoginData(): LiveData<LoginPreferences>
    suspend fun addLoginData(data: LoginPreferences)
    suspend fun clearAllData()


}