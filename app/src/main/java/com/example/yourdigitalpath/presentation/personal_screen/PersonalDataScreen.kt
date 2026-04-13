package com.blqes.digi.presentation.personalscreen

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.example.yourdigitalpath.ui.components.CustomDatePickerField
import com.example.yourdigitalpath.ui.components.PrimaryBlue
import java.util.Calendar

val InputBorder = Color(0xFFE4E8ED)
val HintColor = Color(0xFF9BA3B2)
val WarningYellow = Color(0xFFFDF5E0)

@Composable
fun PersonalDataScreen(
    onBack: () -> Unit = {},
    onNext: () -> Unit = {}
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var fullName by remember { mutableStateOf("") }
    var nationalId by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF435D82))
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.White.copy(alpha = 0.15f),
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { onBack() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "رجوع",
                        tint = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Text(
                    text = "إنشاء حساب",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White
                )
                Spacer(modifier = Modifier.width(40.dp))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "البيانات الشخصية",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .background(Color.White)
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "بيانات الحساب",
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.White.copy(alpha = 0.3f))
                    )
                }
            }
        }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White, RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                RegisterSectionHeader(title = "البيانات الشخصية")
                Spacer(modifier = Modifier.height(20.dp))

                RegisterInputField(
                    label = "الاسم الكامل",
                    value = fullName,
                    onValueChange = { fullName = it },
                    isVerified = fullName.trim().split(" ").size >= 3
                )

                RegisterInputField(
                    label = "الرقم القومي (14 رقم)",
                    value = nationalId,
                    onValueChange = {
                        if (it.length <= 14 && it.all { ch -> ch.isDigit() })
                            nationalId = it
                    },
                    isVerified = nationalId.length == 14
                )

                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(text = "تاريخ الميلاد", color = HintColor, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    CustomDatePickerField(
                        value = birthDate,
                        onValueChange = { birthDate = it },
                        leadingIcon = Icons.Outlined.DateRange,
                        placeholder = "1990 / 01 / 15",
                    )
                }

                RegisterInputField(
                    label = "رقم الهاتف",
                    value = phone,
                    onValueChange = {
                        if (it.length <= 11 && it.all { ch -> ch.isDigit() })
                            phone = it
                    },
                    placeholder = "010XXXXXXXX",
                    isVerified = phone.length == 11,
                    isError = phone.isNotEmpty() && phone.length != 11,
                    errorMessage = "رقم الهاتف غير صحيح"
                )

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    color = WarningYellow,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "تأكد من إدخال بياناتك كما هي في بطاقة الهوية الوطنية لضمان صحة الطلبات",
                            fontSize = 12.sp,
                            color = Color(0xFF8A6A1F),
                            textAlign = TextAlign.End,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            Icons.Outlined.Info,
                            contentDescription = null,
                            tint = Color(0xFF8A6A1F),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onNext,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "التالي — بيانات الحساب",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }

    }
}

@Composable
fun RegisterSectionHeader(title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryBlue
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .width(3.dp)
                .height(20.dp)
                .background(PrimaryBlue, RoundedCornerShape(2.dp))
        )
    }
}

@Composable
fun RegisterInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit = {},
    placeholder: String = "",
    isVerified: Boolean = false,
    isError: Boolean = false,
    errorMessage: String = ""
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text(text = label, color = HintColor, fontSize = 13.sp)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = {
                Text(
                    text = placeholder,
                    color = HintColor,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryBlue,
                unfocusedBorderColor = InputBorder,
                errorBorderColor = Color(0xFFE24B4A)
            ),
            isError = isError,
            trailingIcon = {
                if (isVerified) {
                    Icon(
                        Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF3A7D5A)
                    )
                }
            }
        )
        if (isError && value.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color(0xFFE24B4A),
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}