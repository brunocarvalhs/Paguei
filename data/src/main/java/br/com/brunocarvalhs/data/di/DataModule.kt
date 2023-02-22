package br.com.brunocarvalhs.data.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.data.repositories.CostsRepositoryImpl
import br.com.brunocarvalhs.data.repositories.HomesRepositoryImpl
import br.com.brunocarvalhs.data.repositories.UserRepositoryImpl
import br.com.brunocarvalhs.data.services.AuthenticationService
import br.com.brunocarvalhs.data.services.DataStoreService
import br.com.brunocarvalhs.data.services.SessionManagerService
import br.com.brunocarvalhs.domain.repositories.CostsRepository
import br.com.brunocarvalhs.domain.repositories.HomesRepository
import br.com.brunocarvalhs.domain.repositories.UserRepository
import br.com.brunocarvalhs.domain.services.Authentication
import br.com.brunocarvalhs.domain.services.DataStore
import br.com.brunocarvalhs.domain.services.SessionManager
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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

    @Provides
    fun providerAuthService(service: AuthenticationService): Authentication =
        service

    @Provides
    fun providerUserRepository(repository: UserRepositoryImpl): UserRepository = repository

    @Provides
    fun providerCostsRepository(repository: CostsRepositoryImpl): CostsRepository = repository

    @Provides
    fun providerHomesRepository(repository: HomesRepositoryImpl): HomesRepository = repository
}