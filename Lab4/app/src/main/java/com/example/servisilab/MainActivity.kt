package com.example.servisilab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.servisilab.ui.theme.ServisiLabTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.servisilab.data.entities.Log

class MainActivity : ComponentActivity() {

    private val viewModel: AppViewModel by viewModels {
        AppViewModelFactory((application as ServiceApplication).repository)
    }

    private fun startLoggingService() {
        // TODO: start service and log "Service Started." from viewModel

    }

    private fun stopLoggingService() {
        // TODO: stop service and log "Service Stopped." from viewModel.

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ServisiLabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(viewModel, { startLoggingService() }, { stopLoggingService() })
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: AppViewModel, startFn: () -> Unit, stopFn: () -> Unit) {
    val logs = viewModel.logs.collectAsState(initial = listOf())
    Column {
        Row {
            Button(onClick = startFn, modifier = Modifier.padding(24.dp)) {
                Text(text = "Start service")
            }
            Spacer(modifier = Modifier.weight(1.0f))
            Button(onClick = stopFn, modifier = Modifier.padding(24.dp)) {
                Text(text = "Stop service")
            }
        }
        LazyColumn {
            items(logs.value) { log ->
                LogItem(l = log)
            }
        }
    }
}

@Composable
fun LogItem(l: Log) {
    Card(modifier = Modifier
        .padding(6.dp)
        .fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = l.timestamp, fontStyle = FontStyle.Italic)
            Text(text = l.message, fontWeight = FontWeight.Bold )
        }
    }
}