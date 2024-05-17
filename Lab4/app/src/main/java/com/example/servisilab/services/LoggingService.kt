package com.example.servisilab.services

import com.example.servisilab.ServiceApplication
import com.example.servisilab.data.entities.Log
import com.example.servisilab.data.repositories.LogRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

class LoggingService() {
    private lateinit var app: ServiceApplication
    private lateinit var repo: LogRepository
    private lateinit var job: Job
    private var shouldWriteLogs = false;

    private fun initRepository() {
        // TODO: uncomment line below.
        // app = application as ServiceApplication
        repo = app.repository
    }

    private fun start() {
        initRepository()
        shouldWriteLogs = true
        job = doWork()
    }

    private fun stop() {
        shouldWriteLogs = false
        job.cancel()
    }

    private fun doWork() : Job {
        return CoroutineScope(Dispatchers.IO).launch {
            while (shouldWriteLogs) {
                delay(10000L)
                repo.insert(Log(0, Date().toString(), "Service logging while active."))
            }
        }
    }
}