package com.example.latihanfp1

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("city")
    fun getDataCity() : Call<List<ResponseDataCity>>
}