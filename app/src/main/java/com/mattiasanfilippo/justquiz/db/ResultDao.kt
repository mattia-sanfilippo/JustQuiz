package com.mattiasanfilippo.justquiz.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ResultDao {
    @Query("SELECT * FROM result")
    fun getAll(): List<Result>

    @Insert
    fun insert(result: Result)
}