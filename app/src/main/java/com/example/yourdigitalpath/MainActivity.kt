package com.example.yourdigitalpath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
<<<<<<< HEAD
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.yourdigitalpath.presentation.FileUploadScreen
import com.example.yourdigitalpath.presentation.dataEntry.DataScreen
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestScreen
=======
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.yourdigitalpath.data.dataSource.remote.FirestoreNotificationListener
>>>>>>> development
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
<<<<<<< HEAD
            var currentStep by remember { mutableIntStateOf(1) }

            Scaffold(
                modifier = Modifier.fillMaxSize(),
            ) { innerPadding ->
                when (currentStep) {
                    1 -> ServiceRequestScreen(
                        modifier = Modifier.padding(innerPadding),
                        onNext = { currentStep = 2 },
                        onBack = { finish() }
                    )

                    2 -> DataScreen(
                        modifier = Modifier.padding(innerPadding),
                        onNext = { currentStep = 3 },
                        onBack = { currentStep = 1 }
                    )

                    3 -> FileUploadScreen(
                        modifier = Modifier.padding(innerPadding),
                        onNext = { currentStep = 4 },
                        onBack = { currentStep = 2 }
                    )

                    else -> {}
                }
=======
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {

                }

>>>>>>> development
            }
        }
    }
}
