package com.example.yourdigitalpath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.yourdigitalpath.presentation.AppNavGraph
import com.example.yourdigitalpath.data.dataSource.remote.FirestoreNotificationListener
import com.example.yourdigitalpath.ui.theme.YourDigitalPathTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var firestoreNotificationListener: FirestoreNotificationListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        firestoreNotificationListener.startListening()

        setContent {
            YourDigitalPathTheme  {
                AppNavGraph()
            }
        }
    }
}
