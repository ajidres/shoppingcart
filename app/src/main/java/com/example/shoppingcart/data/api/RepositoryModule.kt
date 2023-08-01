package com.example.shoppingcart.data.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideProductsRepository(apis:EndPoints): ProductsRepository{
            return ProductRepositoryImpl(apis)
    }

}