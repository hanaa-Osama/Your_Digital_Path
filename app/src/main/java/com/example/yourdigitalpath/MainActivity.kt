package com.example.yourdigitalpath

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.yourdigitalpath.presentation.notification.NotificationsScreen
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    TrackingDetailsScreen()
                    NotificationsScreen()
                }
            }
        }

        testFirebase()
    }

    private fun testFirebase() {
        lifecycleScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val db = Firebase.firestore

                    db.collection("test")
                        .document("check")
                        .set(
                            mapOf(
                                "status" to "ok",
                                "timestamp" to System.currentTimeMillis(),
                                "message" to "Firebase شغال تمام! ✅",
                                "app" to "Digital Path"
                            )
                        )
                        .await()

                    Log.d(TAG, "✅ Firebase working perfectly!")
                }
            } catch (e: Exception) {
                Log.e(TAG, "❌ Firebase error", e)
                withContext(Dispatchers.Main) {
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var isLoading by remember { mutableStateOf(true) }
    var statusMessage by remember { mutableStateOf("جاري الاتصال بـ Firebase...") }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2000)
        isLoading = false
        statusMessage = "Firebase متصل بنجاح! ✅"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🚀 Digital Path",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Text(
                    text = statusMessage,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                isLoading = true
                statusMessage = "جاري الاختبار..."
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("اختبار Firebase")
        }
    }
}