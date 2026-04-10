package com.example.yourdigitalpath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.yourdigitalpath.presentation.AppNavGraph
import com.example.yourdigitalpath.data.dataSource.remote.FirestoreNotificationListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // Inject the FirestoreNotificationListener
    @Inject
    lateinit var firestoreNotificationListener: FirestoreNotificationListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        firestoreNotificationListener.startListening()

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {

                }

            }
        }
    }
}
@Composable
fun MyApp() {
    MaterialTheme {
        Surface {
            AppNavGraph()
        }
    }
}