package com.example.shoppingcart.data.proto

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.shoppingcart.CartProducts
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
class ProtoCartDataModule {


    object CartSerializer : Serializer<CartProducts> {
        override val defaultValue: CartProducts = CartProducts.getDefaultInstance()
        override suspend fun readFrom(input: InputStream): CartProducts {
            try {
                return CartProducts.parseFrom(input)
            } catch (exception: InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto.", exception)
            } catch (e: java.io.IOException) {
                e.printStackTrace()
                throw e
            }
        }

        override suspend fun writeTo(t: CartProducts, output: OutputStream) = t.writeTo(output)
    }

    private val Context.loginData: DataStore<CartProducts> by dataStore(
        fileName = "cart_prefs.pb",
        serializer = CartSerializer
    )

    @Provides
    @Reusable
    internal fun providesDataRepository(
        @ApplicationContext context: Context,
    ): ProtoCartRepository {
        return ProtoCartDataRepository(
            context.loginData
        )
    }

}