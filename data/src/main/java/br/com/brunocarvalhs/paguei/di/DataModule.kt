package br.com.brunocarvalhs.paguei.di

import android.content.Context
import br.com.brunocarvalhs.paguei.repositories.CostsRepositoryImpl
import br.com.brunocarvalhs.paguei.repositories.HomesRepositoryImpl
import br.com.brunocarvalhs.paguei.repositories.UserRepositoryImpl
import br.com.brunocarvalhs.paguei.services.AuthenticationService
import br.com.brunocarvalhs.paguei.services.SessionManagerService
import br.com.brunocarvalhs.paguei.domain.repositories.CostsRepository
import br.com.brunocarvalhs.paguei.domain.repositories.HomesRepository
import br.com.brunocarvalhs.paguei.domain.repositories.UserRepository
import br.com.brunocarvalhs.paguei.domain.services.Authentication
import br.com.brunocarvalhs.paguei.domain.services.SessionManager
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
    fun providerFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun providerFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun providerSessionManager(service: SessionManagerService): SessionManager = service

    @Provides
    fun providerAuthService(service: AuthenticationService): Authentication<AuthCredential> =
        service

    @Provides
    fun providerUserRepository(repository: UserRepositoryImpl): UserRepository = repository

    @Provides
    fun providerCostsRepository(repository: CostsRepositoryImpl): CostsRepository = repository

    @Provides
    fun providerHomesRepository(repository: HomesRepositoryImpl): HomesRepository = repository
}