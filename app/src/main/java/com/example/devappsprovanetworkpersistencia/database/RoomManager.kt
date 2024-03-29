package com.example.devappsprovanetworkpersistencia.database

import android.arch.persistence.room.Room
import android.content.Context

object RoomManager {

    fun instance(context: Context) = Room.databaseBuilder(
        context,
         WeatherDatabase::class.java, "db")
        .allowMainThreadQueries() // Permite que o room faça execuções na main thread. ESTA NÃO É UMA BOA PRÁTICA
        .build()
}