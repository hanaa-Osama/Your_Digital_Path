package com.blqes.digi.presentation.personalscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.app.DatePickerDialog
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import java.util.Calendar
import androidx.compose.ui.platform.LocalContext

// Colors
val BackgroundBlue = Color(0xFF1E3A5F)
val InputBorder = Color(0xFF90CAF9)
val WarningYellow = Color(0xFFFFF9C4)
val HintColor = Color(0xFFB0BEC5)

@Composable
fun PersonalDataScreen(string: String) {

    // ✅ STATES
    var fullName by remember { mutableStateOf("") }
    var nationalId by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

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

            SectionHeader(title = "البيانات الشخصية")

            Spacer(modifier = Modifier.height(24.dp))

            // الاسم
            CustomInputField(
                label = "الاسم الكامل",
                value = fullName,
                onValueChange = { fullName = it },
                isVerified = fullName.trim().split(" ").size >= 3
            )

            // الرقم القومي
            CustomInputField(
                label = "الرقم القومي (14 رقم)",
                value = nationalId,
                onValueChange = {
                    if (it.length <= 14 && it.all { ch -> ch.isDigit() }) {
                        nationalId = it
                    }
                },
                isVerified = nationalId.length == 14
            )

            // تاريخ الميلاد
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {


                Text(
                    text = "تاريخ الميلاد",
                    color = HintColor,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(6.dp))


                Box(
                    modifier = Modifier
                        .width(380.dp)


                        .border(
                            width = 1.dp,
                            color = InputBorder,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .clickable {

                            DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    birthDate = "$dayOfMonth/${month + 1}/$year"
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            ).show()

                        }
                        .padding(16.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = if (birthDate.isEmpty()) "اختار التاريخ" else birthDate,
                            color = if (birthDate.isEmpty()) HintColor else Color.Black
                        )

                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    }
                }
            }

            // الهاتف
            CustomInputField(
                label = "رقم الهاتف",
                value = phone,
                onValueChange = {
                    if (it.length <= 11 && it.all { ch -> ch.isDigit() }) {
                        phone = it
                    }
                },
                isVerified = phone.length == 11,


                isError = phone.isNotEmpty() && phone.length != 11,
                errorMessage = "رقم الهاتف غير صحيح"
            )

            Spacer(modifier = Modifier.height(24.dp))

            WarningBox("تأكد من إدخال بياناتك كما هي في بطاقة الهوية الوطنية لضمان صحة الطلبات")

            Spacer(modifier = Modifier.weight(1f))

            NextButton("التالي")
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            color = BackgroundBlue
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(24.dp)
                .background(BackgroundBlue)
        )
    }
}

@Composable
fun CustomInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit = {},
    isVerified: Boolean = false,
    isDate: Boolean = false,
    isEnabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String = ""
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.End
    ) {

        Text(text = label, color = HintColor, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = isEnabled,
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = InputBorder,
                unfocusedBorderColor = InputBorder,
                disabledBorderColor = Color.Transparent
            ),
            trailingIcon = {

                if (isVerified) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF4CAF50))
                } else if (isDate) {
                    Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.Gray)
                }
            }
        )
        if (isError && value.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun WarningBox(text: String) {
    Surface(
        color = WarningYellow.copy(alpha = 0.4f),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontSize = 12.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFFFC107))
        }
    }
}

@Composable
fun NextButton(text: String) {
    Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = BackgroundBlue)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, fontSize = 18.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewScreen() {
    PersonalDataScreen("register_screen")
}