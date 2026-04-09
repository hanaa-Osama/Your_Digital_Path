package com.example.yourdigitalpath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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
            }
        }
    }
}
