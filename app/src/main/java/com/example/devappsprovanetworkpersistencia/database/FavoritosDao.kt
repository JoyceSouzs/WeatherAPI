package com.example.devappsprovanetworkpersistencia.database

import android.arch.persistence.room.*
import com.example.devappsprovanetworkpersistencia.entity.FavoritosCidade


@Dao
interface FavoritosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoritosCidade: FavoritosCidade)

    @Delete
    fun delete(favoritosCidade: FavoritosCidade)

    @Query("SELECT * FROM tb_favoritos_cidade")
    fun selectAll(): List<FavoritosCidade>

    @Query("SELECT * FROM tb_favoritos_cidade WHERE id = :id")
    fun selectById(id: Int): FavoritosCidade
}