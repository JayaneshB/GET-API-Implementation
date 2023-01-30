package com.project.api_implementation.`interface`

import com.project.api_implementation.model.MyDataItems
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("posts")
    fun getData() : Call<List<MyDataItems>>

}