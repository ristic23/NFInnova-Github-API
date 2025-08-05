package com.nfinnovagithub.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType
import com.nfinnovagithub.core.domain.BASE_URL

val networkModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = false
            isLenient = true
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain
                    .request()
                    .newBuilder()
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
            .build()
    }
}