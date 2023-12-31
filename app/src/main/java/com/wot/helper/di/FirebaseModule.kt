package com.wot.helper.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.wot.helper.common.Constants.USERS_REF
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Named
import javax.inject.Singleton

@Module
@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Singleton
    @Provides
    fun provideFirebaseAuthInstance(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        val settings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()
        val db = FirebaseFirestore.getInstance()
        db.firestoreSettings = settings
        return db
    }

    @Singleton
    @Provides
    @Named(USERS_REF)
    fun provideUsersRef(db: FirebaseFirestore): CollectionReference = db.collection(USERS_REF)
}