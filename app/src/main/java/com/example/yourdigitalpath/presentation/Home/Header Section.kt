package com.blqes.digi.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier

) {
    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    Color(
                        0XFF3D5A80
                    )
                )
                .padding(vertical = 30.dp, horizontal = 16.dp)
        ) {
            Column {

                Text("أهلاً بك", color = Color.LightGray)

                Text(
                    "أحمد محمد",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text("استخرج مستنداتك الرسمية بسهولة", color = Color.LightGray)

                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .background(
                            Color(0xFF0F2F5),
                            RoundedCornerShape(18.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(18.dp)
                        )
                        .padding(horizontal = 12.dp)
                        // لون الخلفية وشكل الحواف
                        .padding(horizontal = 8.dp, vertical = 4.dp) // padding جوا المربع
                        .align(Alignment.Start) // لو عايزة يكون على الشمال
                ) {
                    Text(
                        text = "5 خدمات متاحة",
                        fontWeight = FontWeight.Bold,
                        color = Color.White, // لون الخط
                        fontSize = 14.sp
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
    )

}
