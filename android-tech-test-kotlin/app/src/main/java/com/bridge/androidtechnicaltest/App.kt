package com.bridge.androidtechnicaltest

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
//import com.bridge.androidtechnicaltest.di.databaseModule
//import com.bridge.androidtechnicaltest.di.networkModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import javax.inject.Inject

//
//class App1 : Application() {
//
//    private val appComponent : MutableList<Module> = mutableListOf(networkModule, databaseModule)
//
//    override fun onCreate() {
//        super.onCreate()
//
//        startKoin {
//            androidContext(applicationContext)
//            modules(appComponent)
//        }
//    }
//}

@HiltAndroidApp
class App : Application(){
    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(
            this, Configuration.Builder()
                .setWorkerFactory(hiltWorkerFactory).build()
        )

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val notificationChannel =
//                NotificationChannel(CHANNEL, NAME, NotificationManager.IMPORTANCE_DEFAULT)
//
//            val notificationManager =
//                this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//            notificationManager.createNotificationChannel(
//                notificationChannel
//            )
//        }
    }

}
