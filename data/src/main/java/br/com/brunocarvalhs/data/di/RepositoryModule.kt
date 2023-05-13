package br.com.brunocarvalhs.data.di

import br.com.brunocarvalhs.data.repositories.CostsRepositoryImpl
import br.com.brunocarvalhs.data.repositories.GroupsRepositoryImpl
import br.com.brunocarvalhs.data.repositories.UserRepositoryImpl
import br.com.brunocarvalhs.domain.repositories.CostsRepository
import br.com.brunocarvalhs.domain.repositories.GroupsRepository
import br.com.brunocarvalhs.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun providerUserRepository(repository: UserRepositoryImpl): UserRepository = repository

    @Provides
    fun providerCostsRepository(repository: CostsRepositoryImpl): CostsRepository = repository

    @Provides
    fun providerGroupsRepository(repository: GroupsRepositoryImpl): GroupsRepository = repository
}