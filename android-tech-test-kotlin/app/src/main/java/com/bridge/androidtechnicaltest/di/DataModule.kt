package com.bridge.androidtechnicaltest.di

import android.content.Context
import androidx.work.WorkManager
import com.bridge.androidtechnicaltest.data.local.StudentsDao
import com.bridge.androidtechnicaltest.data.local.StudentsDatabase
import com.bridge.androidtechnicaltest.data.remote.PupilApiService
import com.bridge.androidtechnicaltest.data.remote.StudentApiService
import com.bridge.androidtechnicaltest.data.repository.StudentRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val API_TIMEOUT: Long = 30

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
        val builder = OkHttpClient.Builder()
        builder.readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
        builder.writeTimeout(API_TIMEOUT, TimeUnit.SECONDS)
        builder.connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)

        val requestId = "dda7feeb-20af-415e-887e-afc43f245624"
        val userAgent = "Bridge Android Tech Test"
        val requestInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                    .addHeader("X-Request-ID", requestId)
                    .addHeader("User-Agent", userAgent)
                    .build()
            chain.proceed(newRequest)
        }
       builder.addInterceptor(requestInterceptor)
        return  Retrofit.Builder().client(builder.build())
            .baseUrl("https://androidtechnicaltestapi-test.bridgeinternationalacademies.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideAStudentApiService(retrofit:Retrofit):PupilApiService{
        return retrofit.create(PupilApiService::class.java)
    }


    @Singleton
    @Provides
    fun provideDatebase(@ApplicationContext context:Context): StudentsDatabase {
        return StudentsDatabase.getInstance(context)

    }



    @Provides
    fun provideStudentDao(studentDatabase:  StudentsDatabase):StudentsDao{
        return  studentDatabase.getPupilsDao()
    }

    @Provides
    @Singleton
    fun provideWorkmanager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    fun provideStudentsRepository(workManager: WorkManager, quoteDao:StudentsDao): StudentRepoImpl {
        return StudentRepoImpl(workManager, quoteDao)
    }
}