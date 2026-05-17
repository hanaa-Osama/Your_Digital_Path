package com.example.yourdigitalpath.presentation.Home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.domain.model.Event
import com.example.yourdigitalpath.ui.theme.AppColors

@Composable
fun EventCard(
    event: Event,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(AppColors.Surface)
            .border(0.5.dp, AppColors.Border, RoundedCornerShape(14.dp))
            .clickable { navController.navigate(event.route) }
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(AppColors.PrimaryLight, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = event.icon,
                contentDescription = null,
                tint = AppColors.Primary,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = event.title),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = AppColors.TextPrimary
        )
        Text(
            text = stringResource(id = event.subtitle),
            fontSize = 11.sp,
            color = AppColors.TextHint
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .background(AppColors.PrimaryLight, RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            Text(
                text = stringResource(id = event.price),
                fontWeight = FontWeight.SemiBold,
                color = AppColors.Primary,
                fontSize = 12.sp
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun EventCardPreview() {
    val sampleEvent = Event(
        title = R.string.birth_certificate,
        subtitle = R.string.birth_certificate_subtitle,
        color = Color(0xFF2ED1C0),
        icon = Icons.Default.Star,
        price = R.string.egp_20,
        route = ""
    )
    EventCard(
        event = sampleEvent,
        navController = rememberNavController()
    )
}