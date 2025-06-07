package com.example.wherewatch_frontend.di


import com.example.wherewatch_frontend.data.source.remote.apis.MovieApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Koin module that provides the required dependencies for Retrofit, Gson, and MovieApi.
 *
 * This module defines singleton instances for:
 * - Gson: with lenient parsing for flexible JSON deserialization.
 * - OkHttpClient: HTTP client used by Retrofit.
 * - Retrofit: network client configured to connect to the local backend.
 * - MovieApi: interface for accessing movie-related API endpoints.
 *
 * The base URL is set to connect to a local server using an Android emulator.
 */
val retrofitModule = module {
    /**
     * Provides a singleton instance of Gson with lenient configuration.
     */
    single<Gson> {
        GsonBuilder()
            .setLenient()
            .create()
    }
    /**
     * Provides a singleton instance of OkHttpClient.
     */
    single {
        OkHttpClient.Builder()
            .build()
    }
    /**
     * Provides a singleton instance of Retrofit configured with:
     * - A base URL pointing to the local server (`http://10.0.2.2:8080/`)
     * - The OkHttpClient instance
     * - A Gson converter for JSON serialization and deserialization
     */
    single {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }
    /**
     * Provides an implementation of the MovieApi interface using Retrofit.
     */
    single<MovieApi> {
        get<Retrofit>().create(MovieApi::class.java)
    }

}