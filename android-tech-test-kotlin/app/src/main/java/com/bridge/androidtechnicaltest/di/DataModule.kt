package com.bridge.androidtechnicaltest.di

import android.content.Context
import androidx.work.WorkManager
import com.bridge.androidtechnicaltest.data.local.PupilsDao
import com.bridge.androidtechnicaltest.data.local.StudentsDatabase
import com.bridge.androidtechnicaltest.data.remote.PupilApiService
import com.bridge.androidtechnicaltest.domain.repository.PupilRepository1
import com.bridge.androidtechnicaltest.domain.usecases.GetPupilsUsecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
private const val API_TIMEOUT = 30L



@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val requestInterceptor = Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("X-Request-ID", "dda7feeb-20af-415e-887e-afc43f245624")
                .addHeader("User-Agent", "Bridge Android Tech Test")
                .build()
            chain.proceed(newRequest)
        }

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(requestInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://androidtechnicaltestapi-test.bridgeinternationalacademies.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providePupilApiService(retrofit: Retrofit): PupilApiService {
        return retrofit.create(PupilApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): StudentsDatabase {
        return StudentsDatabase.getInstance(context)
    }

    @Provides
    fun provideStudentDao(database: StudentsDatabase): PupilsDao {
        return database.getPupilsDao()
    }

    @Singleton
    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    fun GetPupils(pupilRepo: PupilRepository1): GetPupilsUsecase {
        return GetPupilsUsecase(pupilRepo)
    }
}