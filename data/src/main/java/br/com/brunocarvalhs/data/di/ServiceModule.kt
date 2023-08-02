package br.com.brunocarvalhs.data.di

import br.com.brunocarvalhs.data.services.AdsServiceImpl
import br.com.brunocarvalhs.data.services.AnalyticsServiceImpl
import br.com.brunocarvalhs.data.services.AuthenticationService
import br.com.brunocarvalhs.data.services.NotificationServiceImpl
import br.com.brunocarvalhs.data.services.UpdateVersionService
import br.com.brunocarvalhs.domain.services.AdsService
import br.com.brunocarvalhs.domain.services.AnalyticsService
import br.com.brunocarvalhs.domain.services.Authentication
import br.com.brunocarvalhs.domain.services.NotificationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun providerAnalyticsService(service: AnalyticsServiceImpl): AnalyticsService = service

    @Provides
    fun providerAuthService(service: AuthenticationService): Authentication =
        service

    @Provides
    @Singleton
    fun providerNotificationService(service: NotificationServiceImpl): NotificationService = service

    @Provides
    @Singleton
    fun providerAdsService(service: AdsServiceImpl): AdsService = service

    @Provides
    fun providerUpdateVersionService(service: UpdateVersionService): UpdateVersionService = service
}