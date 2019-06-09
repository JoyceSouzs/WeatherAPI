package com.example.devappsprovanetworkpersistencia.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.devappsprovanetworkpersistencia.entity.FavoritosCidade

@Database(entities = [FavoritosCidade::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun favoritosDao(): FavoritosDao
}