package com.example.shoppingcart.domain.usesCases

import com.example.shoppingcart.data.api.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideRegisterUseCase(
        registerRepository: ProductsRepository
    ): ProductsUseCase {
        return ProductsUseCaseImpl(registerRepository)
    }


}