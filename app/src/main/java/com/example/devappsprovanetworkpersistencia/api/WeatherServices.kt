package com.example.devappsprovanetworkpersistencia.api

import com.example.devappsprovanetworkpersistencia.entity.Resultado
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherServices {

    @GET("find")
    fun find(
        @Query("q")
        cityName: String,

        @Query("units")
        unidade: String,

        @Query("lang")
        ling: String,

        @Query("appid")
        appKey: String): Call<Resultado>

    //Grupo de cidades no favoritos
    @GET("group")
    fun group(
        @Query("id")
        cityId: String,

        @Query("units")
        unidade: String,

        @Query("lang")
        ling: String,

        @Query("appid")
        appKey: String): Call<Resultado>

    //Idioma
    @GET("daily")
    fun daily(

        @Query("lang")
        ling: String,

        @Query("appid")
        appKey: String)

}