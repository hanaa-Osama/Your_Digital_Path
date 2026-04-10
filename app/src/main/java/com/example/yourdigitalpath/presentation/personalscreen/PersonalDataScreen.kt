package com.blqes.digi.presentation.personalscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


val BackgroundBlue = Color(0xFF1E3A5F)
val CardBackground = Color(0xFFFFFFFF)
val InputBorder = Color(0xFF90CAF9)
val WarningYellow = Color(0xFFFFF9C4)
val HintColor = Color(0xFFB0BEC5)

@Composable
fun PersonalDataScreen(string: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBlue)
            .padding(top = 40.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
                .padding(24.dp),
            horizontalAlignment = Alignment.End
        ) {
            // العنوان الرئيسي
            SectionHeader(title = "البيانات الشخصية")

            Spacer(modifier = Modifier.height(24.dp))

            // الحقول
            CustomInputField(
                label = "الاسم الكامل",
                value = "سارة محمد عبد الله",
                isVerified = true
            )
            CustomInputField(
                label = "الرقم القومي (14 رقم)",
                value = "2990115012345XX",
                isVerified = true
            )
            CustomInputField(label = "تاريخ الميلاد", value = "1990 / 01 / 15", isDate = true)
            CustomInputField(label = "رقم الهاتف", value = "010XXXXXXXX", isEnabled = false)

            Spacer(modifier = Modifier.height(24.dp))

            // صندوق التنبيه (Warning Box)
            WarningBox(text = "تأكد من إدخال بياناتك كما هي في بطاقة الهوية الوطنية لضمان صحة الطلبات")

            Spacer(modifier = Modifier.weight(1f))

            // الزر السفلي
            NextButton(text = "التالي ")
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = BackgroundBlue
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(24.dp)
                .background(BackgroundBlue, RoundedCornerShape(2.dp))
        )
    }
}

@Composable
fun CustomInputField(
    label: String,
    value: String,
    isVerified: Boolean = false,
    isDate: Boolean = false,
    isEnabled: Boolean = true
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text(text = label, color = HintColor, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedCard(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.outlinedCardColors(
                containerColor = if (isEnabled) Color.White else Color(0xFFF5F5F5)
            ),
            border = CardDefaults.outlinedCardBorder(isEnabled).copy(
                brush = androidx.compose.ui.graphics.SolidColor(if (isEnabled) InputBorder else Color.Transparent)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = value,
                    color = if (isEnabled) Color.Black else HintColor,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )

                if (isVerified) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF4CAF50)
                    )
                } else if (isDate) {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = null,
                        tint = Color(0xFF4CAF50)
                    )
                }
            }
        }
    }
}

@Composable
fun WarningBox(text: String) {
    Surface(
        color = WarningYellow.copy(alpha = 0.3f),
        shape = RoundedCornerShape(12.dp),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFFFD54F))
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = text,
                fontSize = 12.sp,
                color = Color(0xFF827717),
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFFFD54F))
        }
    }
}

@Composable
fun NextButton(text: String) {
    Button(
        onClick = { /* Navigate */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = BackgroundBlue)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Preview(
    name = "Personal Data Screen",
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=411dp,height=891dp,dpi=420"
)
@Composable
fun FullPersonalDataScreenPreview() {
    MaterialTheme {
        Surface {
            PersonalDataScreen("register_screen")
        }
    }
}