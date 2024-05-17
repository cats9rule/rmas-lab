package com.example.servisilab.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.servisilab.data.entities.Log
import kotlinx.coroutines.flow.Flow

@Dao
interface LogDao {
    @Insert
    suspend fun insert(log: Log)

    @Delete
    suspend fun delete(log: Log)

    @Update
    suspend fun update(log: Log)

    @Query("SELECT * FROM logs ORDER BY id DESC")
    fun getLogs(): Flow<List<Log>>

    @Query("DELETE FROM logs")
    suspend fun deleteAll()

    @Query("SELECT * FROM logs WHERE message=:message")
    suspend fun getLog(message: String) : Log
}