package com.example.servisilab

import android.app.Application
import com.example.servisilab.data.database.LogDatabase
import com.example.servisilab.data.repositories.LogRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ServiceApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { LogDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { LogRepository(database.logDao()) }
}