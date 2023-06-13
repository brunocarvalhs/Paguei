package br.com.brunocarvalhs.data.di

import android.content.Context
import android.content.SharedPreferences
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.data.services.*
import br.com.brunocarvalhs.domain.services.*
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun providerFirebase(@ApplicationContext context: Context): FirebaseApp? =
        FirebaseApp.initializeApp(context)

    @Provides
    @Singleton
    fun providerSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(context.applicationInfo.packageName, Context.MODE_PRIVATE)

    @Provides
    fun providerFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun providerFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    fun providerFirebaseAnalytics(): FirebaseAnalytics = Firebase.analytics

    @Provides
    fun providerFirebaseNotification(): FirebaseMessaging = Firebase.messaging

    @Provides
    @Singleton
    fun providerNavigation(
        @ApplicationContext context: Context,
        sessionManager: SessionManager
    ): Navigation = Navigation(context, sessionManager)

    @Provides
    @Singleton
    fun providerDataStore(service: DataStoreService): DataStore = service

    @Provides
    @Singleton
    fun providerSessionManager(service: SessionManagerService): SessionManager = service
}