package com.example.wherewatch_frontend.di

import com.example.wherewatch_frontend.WhereWhatch
import org.koin.dsl.module
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val retrofitModule = module {
    single<Gson> {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single {
        OkHttpClient.Builder()
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    // Ejemplo de servicio
    single<WhereWhatch> {
        get<Retrofit>().create(WhereWhatch::class.java)
    }

}