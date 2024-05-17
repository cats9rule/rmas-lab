package com.example.servisilab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.servisilab.data.entities.Log
import com.example.servisilab.data.repositories.LogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Date

class AppViewModel(private val repository: LogRepository) : ViewModel() {

    val logs : Flow<List<Log>> = repository.logs

    fun addLog(message: String) {
        val time = Date()
        val log = Log(0, time.toString(), message)

        viewModelScope.launch {
            repository.insert(log)
        }

    }
}

class AppViewModelFactory(private val repository: LogRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            return AppViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}