package com.example.shoppingcart.data.proto

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.shoppingcart.LoginPreferences
import com.google.protobuf.InvalidProtocolBufferException
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.InputStream
import java.io.OutputStream

@Module
@InstallIn(SingletonComponent::class)
class ProtoUserModule {


    object LoginSerializer : Serializer<LoginPreferences> {
        override val defaultValue: LoginPreferences = LoginPreferences.getDefaultInstance()
        override suspend fun readFrom(input: InputStream): LoginPreferences {
            try {
                return LoginPreferences.parseFrom(input)
            } catch (exception: InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto.", exception)
            } catch (e: java.io.IOException) {
                e.printStackTrace()
                throw e
            }
        }

        override suspend fun writeTo(t: LoginPreferences, output: OutputStream) = t.writeTo(output)
    }

    private val Context.loginData: DataStore<LoginPreferences> by dataStore(
        fileName = "login_prefs.pb",
        serializer = LoginSerializer
    )

    @Provides
    @Reusable
    internal fun providesDataRepository(
        @ApplicationContext context: Context,
    ): ProtoUserRepository {
        return ProtoUserDataRepository(
            context.loginData
        )
    }

}