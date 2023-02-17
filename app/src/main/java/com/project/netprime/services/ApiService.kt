package com.project.netprime.services

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType


class ApiService {
    companion object {
        private const val BASE_URL =  "https://api.themoviedb.org"
        private var retrofit : Retrofit?=null

        fun getInstance() : Retrofit{
            if(retrofit==null) {
                retrofit=Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getHttpClient())
                    .addConverterFactory(JsonConverter.json.asConverterFactory("application/json".toMediaType()))
                    .build()
            }
            return retrofit!!
        }

        private fun getHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level=(HttpLoggingInterceptor.Level.BODY)
            return OkHttpClient.Builder().addInterceptor(interceptor).build()
        }
    }
}

object JsonConverter {
    val json: Json = Json { ignoreUnknownKeys = true }
}