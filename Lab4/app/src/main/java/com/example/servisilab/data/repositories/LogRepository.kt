package com.example.servisilab.data.repositories

import androidx.annotation.WorkerThread
import com.example.servisilab.data.daos.LogDao
import com.example.servisilab.data.entities.Log
import kotlinx.coroutines.flow.Flow

class LogRepository(private val logDao: LogDao) {

    val logs: Flow<List<Log>> = logDao.getLogs()

    @WorkerThread
    suspend fun insert(log: Log) {
        logDao.insert(log)
    }

    @WorkerThread
    suspend fun update(log: Log) {
        logDao.update(log)
    }

    @WorkerThread
    suspend fun delete(log: Log) {
        logDao.delete(log)
    }
}