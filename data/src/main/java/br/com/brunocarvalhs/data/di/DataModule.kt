package br.com.brunocarvalhs.data.di

import android.content.Context
import br.com.brunocarvalhs.data.repositories.UserRepositoryImpl
import br.com.brunocarvalhs.data.services.AuthServiceImpl
import br.com.brunocarvalhs.data.services.SocialGoogleLoginService
import br.com.brunocarvalhs.payflow.domain.repositories.UserRepository
import br.com.brunocarvalhs.payflow.domain.services.AuthService
import br.com.brunocarvalhs.payflow.domain.services.SocialGoogleLogin
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
    fun providerAuthService(service: AuthServiceImpl): AuthService<AuthCredential> = service

    @Provides
    fun providerSocialGoogleLogin(service: SocialGoogleLoginService): SocialGoogleLogin = service

    @Provides
    fun providerUserRepository(repository: UserRepositoryImpl): UserRepository = repository
}