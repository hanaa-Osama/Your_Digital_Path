package com.example.yourdigitalpath.presentation.orders_history.order_component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.HeartBroken
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yourdigitalpath.ui.theme.AppColors

@Composable
fun ServiceIconBadge(serviceName: String) {
    val icon = when {
        serviceName.contains("ميلاد") -> Icons.Outlined.Article
        serviceName.contains("هوية")  -> Icons.Outlined.Badge
        serviceName.contains("زواج")  -> Icons.Outlined.Favorite
        serviceName.contains("وفاة")  -> Icons.Outlined.Person
        serviceName.contains("طلاق")  -> Icons.Outlined.HeartBroken
        else                          -> Icons.Outlined.Article
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(AppColors.PrimaryLight)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = serviceName,
            tint = AppColors.Primary,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview
@Composable
fun PreviewServiceIconBadge(modifier: Modifier = Modifier) {
    ServiceIconBadge(serviceName = "ميلاد")
}