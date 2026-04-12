package com.blqes.digi.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.domain.model.OrderModel

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier,
    userName: String = "هناء اسامة",
    orders: List<OrderModel> = emptyList()) {
    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(Color(0xFF3D5A80))
                .padding(top = 40.dp, bottom = 30.dp, start = 20.dp, end = 20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "أهلاً بك",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 16.sp
                )

                Text(
                    text = "هناء اسامة",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Text(
                    text = "استخرج مستنداتك الرسمية بسهولة",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .background(
                            color = Color.White.copy(0.1f),
                            shape = RoundedCornerShape(25.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color.Gray.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(25.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color(0xFF4CAF50), shape = CircleShape)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = " ${orders.size} خدمات متاحة",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun HeaderSectionPrev() {
    HeaderSection(
        userName = "هناء اسامه",

    )

}
