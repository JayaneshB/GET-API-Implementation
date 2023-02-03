package com.project.moviebuff_demo.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiService {
    companion object {
        private const val BASE_URL =  "https://api.themoviedb.org"
        private var retrofit : Retrofit?=null

        fun getInstance() : Retrofit{
            if(retrofit==null) {
                retrofit=Retrofit.Builder()
                    .baseUrl(BASE_URL).client(getHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }

        private fun getHttpClient():OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            return OkHttpClient.Builder().addInterceptor(interceptor).build()
        }
    }
}