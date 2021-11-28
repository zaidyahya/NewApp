package com.example.theapp.di

import com.example.theapp.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * This class is setup to bind the interface that are dependencies. Explanation :- https://developer.android.com/codelabs/android-hilt#7
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCatalogueRepository(
        catalogueRepository: CatalogueRepository
    ): ICatalogueRepository

    @Binds
    abstract fun bindCustomerRepository(
        customerRepository: CustomerRepository
    ): ICustomerRepository

    @Binds
    abstract fun bindOrderRepository(
        orderRepository: OrderRepository
    ): IOrderRepository

}