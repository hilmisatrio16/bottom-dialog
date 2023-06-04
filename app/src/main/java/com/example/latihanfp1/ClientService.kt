package com.example.latihanfp1

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientService {

    private const val  BASE_URL = "https://647c4747c0bae2880ad085ae.mockapi.io/"

    val instance : ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}