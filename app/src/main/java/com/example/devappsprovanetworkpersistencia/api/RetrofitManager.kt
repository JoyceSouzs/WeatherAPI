package com.example.devappsprovanetworkpersistencia.api

import com.example.devappsprovanetworkpersistencia.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {

    //Cria uma instância do Retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    //Cria a implementação da interface WeateherServices
    fun weatherService() = retrofit.create(
        WeatherServices::class.java)

}