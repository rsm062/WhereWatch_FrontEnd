package com.example.wherewatch_frontend

import android.app.Application
import com.example.wherewatch_frontend.di.appModule
import com.example.wherewatch_frontend.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

/**
 * Custom Application class to initialize the Koin dependency injection framework.
 *
 * On application start, it configures Koin with the application context and
 * loads the app's dependency injection modules.
 */
class WhereWatch : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WhereWatch)
            modules(
                appModule,
                retrofitModule
            )
        }
    }
}