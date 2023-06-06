package com.example.latihanfp1

import com.example.latihanfp1.model.ResponseDataCity
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("city")
    fun getDataCity() : Call<List<ResponseDataCity>>
}