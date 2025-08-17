package com.wot.helper.di

import android.app.Application
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.wot.helper.common.Constants.IO_DISPATCHER
import com.wot.helper.data.AuthRepositoryImpl
import com.wot.helper.domain.models.repository.AuthRepository
import com.wot.helper.domain.models.use_case.auth.ValidateConfirmedPassword
import com.wot.helper.domain.models.use_case.auth.ValidateEmail
import com.wot.helper.domain.models.use_case.auth.ValidatePassword
import com.wot.helper.domain.models.use_case.auth.ValidateUsername
import com.wot.helper.domain.models.use_case.auth.ValidationUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
        auth: FirebaseAuth
    ): AuthRepository = AuthRepositoryImpl(googleSignInClient, auth)

    @Singleton
    @Provides
    @Named(IO_DISPATCHER)
    fun provideCoroutineDispatcher(): CoroutineDispatcher =
        Dispatchers.IO

    @Singleton
    @Provides
    fun provideValidationUseCases() = ValidationUseCases(
        validateEmail = ValidateEmail(),
        validateConfirmedPassword = ValidateConfirmedPassword(),
        validatePassword = ValidatePassword(),
        validateUsername = ValidateUsername()
    )
}