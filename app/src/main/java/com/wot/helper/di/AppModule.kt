package com.wot.helper.di

import android.app.Application
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.wot.helper.common.Constants
import com.wot.helper.common.Constants.IO_DISPATCHER
import com.wot.helper.common.Constants.USERS_REF
import com.wot.helper.data.AuthRepositoryImpl
import com.wot.helper.domain.models.repository.AuthRepository
import com.wot.helper.domain.models.use_case.auth.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.io.File
import javax.inject.Named
import javax.inject.Singleton


@Module
@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideAuthRepository(
        googleSignInClient: GoogleSignInClient,
        auth: FirebaseAuth,
        @Named(USERS_REF) usersRef: CollectionReference
    ): AuthRepository = AuthRepositoryImpl(googleSignInClient, auth, usersRef)

    @Singleton
    @Provides
    @Named(IO_DISPATCHER)
    fun provideCoroutineDispatcher(): CoroutineDispatcher =
        Dispatchers.IO

    @Singleton
    @Provides
    fun provideAuthUseCases(repository: AuthRepository) = AuthUseCases(

        getAuthState = GetAuthState(repository),
        isLoggedIn = IsLoggedIn(repository),
        getUserProfile = GetUserProfile(repository),
        signInWithGoogle = SignInWithGoogle(repository),
        signOut = SignOut(repository),
        resetPassword = ResetPassword(repository),
        signInWithEmail = SignInWithEmail(repository),
        register = Register(repository)
    )

    @Singleton
    @Provides
    fun provideValidationUseCases() = ValidationUseCases(
        validateEmail = ValidateEmail(),
        validateConfirmedPassword = ValidateConfirmedPassword(),
        validatePassword = ValidatePassword(),
        validateUsername = ValidateUsername()
    )
}