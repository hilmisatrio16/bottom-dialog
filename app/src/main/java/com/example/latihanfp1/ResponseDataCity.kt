package com.example.latihanfp1


import com.google.gson.annotations.SerializedName

data class ResponseDataCity(
    @SerializedName("destination")
    val destination: String,
    @SerializedName("id")
    val id: String
)