package com.example.wherewatch_frontend

import android.app.Application
import com.example.wherewatch_frontend.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class WhereWhatch : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WhereWhatch)
            modules(retrofitModule)
        }
    }
}