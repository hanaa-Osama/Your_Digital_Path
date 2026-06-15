package com.example.yourdigitalpath.presentation.notification.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.presentation.notification.NotificationViewModel
import com.example.yourdigitalpath.ui.theme.AppColors

data class NotificationItemData(
    val title: String,
    val desc: String,
    val time: String,
    val icon: ImageVector,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    notificationViewModel: NotificationViewModel,

) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedNotificationId by remember { mutableStateOf<String?>(null) }
    val notifications by notificationViewModel.notifications.collectAsState()


    Scaffold(
        containerColor = AppColors.Background,
        topBar = {
            val primaryColor = AppColors.Primary
            val headerGradientColors = if (com.example.yourdigitalpath.ui.theme.LocalDarkTheme.current) {
                listOf(Color(0xFF1D2A44), Color(0xFF0F1929))
            } else {
                listOf(primaryColor, Color(0xFF293241))
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = headerGradientColors
                        )
                    )
                    .padding(horizontal = 24.dp, vertical = 24.dp)
            ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.notifications_center),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                        Text(
                            text = stringResource(
                                R.string.new_notifications_count,
                                notifications.size
                            ),
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
            }
        },
    ) { padding ->
        if (notifications.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.NotificationsNone,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = AppColors.TextHint
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        stringResource(R.string.no_notifications),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = AppColors.TextSecond
                    )
                }
            }
        } else {

            if (showDeleteDialog) {
                androidx.compose.material3.AlertDialog(
                    onDismissRequest = {
                        showDeleteDialog = false
                        selectedNotificationId = null
                    },
                    title = {
                        Text(
                            text = stringResource(
                                R.string.delete_confirmation
                            )
                        )
                    },
                    text = {
                        Text(
                            text = stringResource(
                                R.string.delete_notification_message
                            )
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                selectedNotificationId?.let {
                                    notificationViewModel.deleteNotification(it)
                                }
                                showDeleteDialog = false
                                selectedNotificationId = null
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.yes)
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDeleteDialog = false
                                selectedNotificationId = null
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.cancel)
                            )
                        }
                    }
                )
            }

            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = notifications,
                    key = { it.id }
                ) { notification ->

                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = { value ->
                            if (value == SwipeToDismissBoxValue.EndToStart) {
                                selectedNotificationId = notification.id
                                showDeleteDialog = true
                                // نرجع false عشان الإشعار يفضل موجود وميروحش غير لما نضغط "نعم"
                                false
                            } else {
                                false
                            }
                        }
                    )

                    LaunchedEffect(showDeleteDialog) {
                        if (!showDeleteDialog && selectedNotificationId == null) {
                            dismissState.reset()
                        }
                    }

                    SwipeToDismissBox(
                        state = dismissState,
                        enableDismissFromStartToEnd = false,
                        backgroundContent = {
                            val color = when (dismissState.dismissDirection) {
                                SwipeToDismissBoxValue.EndToStart -> Color(0xFFFFEBEE)
                                else -> Color.Transparent
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color, RoundedCornerShape(16.dp))
                                    .padding(horizontal = 20.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                                    Icon(
                                        imageVector = Icons.Default.DeleteOutline,
                                        contentDescription = stringResource(R.string.delete),
                                        tint = Color(0xFFF04438)
                                    )
                                }
                            }
                        }
                    ) {

                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {

                            Row(
                                modifier = Modifier
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(
                                            color = AppColors.Primary,
                                            shape = CircleShape
                                        )
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = null,
                                    tint = AppColors.TextHint
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {

                                    Text(
                                        text = notification.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = AppColors.TextPrimary
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = notification.message,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AppColors.TextSecond
                                    )
                                }

                                Text(
                                    text = notification.timeAgo,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AppColors.TextHint
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
