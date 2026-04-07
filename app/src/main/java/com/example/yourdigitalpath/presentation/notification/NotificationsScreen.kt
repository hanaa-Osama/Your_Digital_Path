package com.example.yourdigitalpath.presentation.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.yourdigitalpath.presentation.notification.component.NotificationCard
import com.example.yourdigitalpath.ui.theme.LightGrayBg
import com.example.yourdigitalpath.ui.theme.SuccessGreen

data class NotificationItemData(
    val title: String,
    val desc: String,
    val time: String,
    val icon: ImageVector,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen() {
    val items = listOf(
        NotificationItemData(
            "تم إصدار مستندك",
            "شهادة الميلاد جاهزة للاستلام. سيتم التوصيل خلال 2-3 أيام.",
            "منذ 10 دقائق",
            Icons.Default.Check,
            Color(0xFF3F51B5)
        ),
        NotificationItemData(
            "مطلوب إجراء",
            "طلب تجديد الهوية يحتاج استكمال رفع صورة جواز السفر.",
            "منذ ساعتين",
            Icons.Default.PriorityHigh,
            Color(0xFFFFA000)
        ),
        NotificationItemData(
            "تأكيد الدفع",
            "تم خصم 40 جنيه بنجاح لطلب شهادة الميلاد رقم REQ-2025-00842.",
            "5 أبريل 2025",
            Icons.Default.Payment,
            SuccessGreen
        ),
        NotificationItemData(
            "تذكير - تحديث البيانات",
            "بطاقة هويتك تنتهي خلال 30 يوماً. جدد الآن لتجنب الغرامات.",
            "3 أبريل 2025",
            Icons.Default.NotificationsNone,
            Color.Gray
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "الإشعارات",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.DeleteSweep,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(LightGrayBg),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items) { notification ->
                NotificationCard(notification)
            }
        }
    }
}
