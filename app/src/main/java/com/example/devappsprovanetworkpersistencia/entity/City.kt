package com.example.devappsprovanetworkpersistencia.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class Resultado(
    var list: List<City>)

data class City(
    var id: Int,
    var name: String,

    @SerializedName("weather") var weathers: List<Weather>
    ,@SerializedName("sys") var pais: Pais
    ,@SerializedName("main") var temperatura: Temperaturas
    ,@SerializedName("clouds") var nuvem : Nuvens
    ,@SerializedName("wind") var vento : Vento)


data class Weather(var main: String, var icon: String, var description: String)
data class Temperaturas(var temp: String, var pressure: String)
data class Pais(var country: String)
data class Nuvens(var all: Double)
data class Vento(var speed: Double)

//Tabela para base de dados - ROOM
@Entity(tableName = "tb_favoritos_cidade")
data class FavoritosCidade(
    @PrimaryKey
    var id: Int,
    @ColumnInfo(name = "city_name")
    var name: String)