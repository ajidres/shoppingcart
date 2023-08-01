package com.example.shoppingcart.data.proto

import androidx.datastore.core.DataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.shoppingcart.LoginPreferences
import javax.inject.Inject

class ProtoUserDataRepository @Inject constructor(
    private val dataStore: DataStore<LoginPreferences>
) : ProtoUserRepository {


    override suspend fun getLoginData(): LiveData<LoginPreferences> {
        return dataStore.data.asLiveData()
    }

    override suspend fun addLoginData(data: LoginPreferences) {
        dataStore.updateData { loginPreferences: LoginPreferences ->
            loginPreferences.toBuilder().clear().setUser(data.user).setPassword(data.password).setLogged(data.logged).build()
        }
    }

    override suspend fun clearAllData() {
        dataStore.updateData {loginPreferences: LoginPreferences ->
            loginPreferences.toBuilder().clear().build()
        }
    }
}