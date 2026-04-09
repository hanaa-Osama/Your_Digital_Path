package com.example.yourdigitalpath

import android.os.Bundle
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.data.dataSource.remote.FirestoreNotificationListener
import com.example.yourdigitalpath.presentation.tracking.TrackingViewModel
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
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var showTracking by remember { mutableStateOf(false) }
//                    val launcher = rememberLauncherForActivityResult(
//                        contract = ActivityResultContracts.RequestPermission(),
//                        onResult = { /* معالجة النتيجة إذا أردتِ */ }
//                    )
//
//                    LaunchedEffect(Unit) {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
//                        }
//                    }

//                    NotificationsScreen(notificationViewModel = viewModel())
//                    TrackingDetailsScreen(orderId = "123")
//                    val testOrderId = "REQ-2025-00842"
//                    if (!showTracking) {
//                        // الشاشة الأولى: إدخال البيانات
//                        RequestServiceScreen(onOrderSent = {
//                            showTracking = true
//                        })
//                    } else {
//                        // الشاشة الثانية: صفحتك (التتبع)
//                        TrackingDetailsScreen(orderId = testOrderId)
//                    }

                }

            }
        }
    }
}


@Composable
fun RequestServiceScreen(
    viewModel: TrackingViewModel = hiltViewModel(),
    onOrderSent: () -> Unit
) {


    var serviceName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "تقديم طلب جديد",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1D2939)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // خانة إدخال اسم الخدمة
        OutlinedTextField(
            value = serviceName,
            onValueChange = { serviceName = it },
            label = {
                Text("نوع الخدمة (مثلاً: هوية رقمية)")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // زرار الإرسال
        Button(
            onClick = {
                if (serviceName.isNotBlank()) {
                    // 1. نبعت الداتا للفايربيز
                    viewModel.createOrderTest(serviceName)
                    // 2. نخبر الـ MainActivity إننا خلصنا عشان يقلب الشاشة
                    onOrderSent()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003366))
        ) {
            Text("إرسال الطلب", fontSize = 18.sp, color = Color.White)
        }
    }
}