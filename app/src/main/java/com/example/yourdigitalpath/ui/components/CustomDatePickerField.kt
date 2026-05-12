package com.example.yourdigitalpath.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    errorMessage: String? = null
) {
    var showDatePicker by remember { mutableStateOf(false) }

    val today = remember { Calendar.getInstance().timeInMillis }
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= today
            }

            override fun isSelectableYear(year: Int): Boolean {
                return year <= Calendar.getInstance().get(Calendar.YEAR)
            }
        }
    )

    val isError = errorMessage != null

    val selectedDateString = remember(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let {
            val date = Date(it)
            val format = SimpleDateFormat("yyyy / MM / dd", Locale.getDefault())
            format.timeZone = TimeZone.getTimeZone("UTC")
            format.format(date)
        } ?: value
    }

    val confirmEnabled = remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
                .border(
                    1.dp,
                    if (isError) Color.Red else Color.Transparent,
                    RoundedCornerShape(12.dp)
                )
                .clickable { showDatePicker = true }
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                leadingIcon?.let {
                    Icon(it, contentDescription = null, tint = Color.Gray)
                    Spacer(modifier = Modifier.width(12.dp))
                }
                Text(
                    text = value.ifEmpty { placeholder },
                    color = if (value.isEmpty()) Color.Gray.copy(alpha = 0.7f) else Color(0xFF001F3F),
                    fontSize = 14.sp
                )
            }
        }

        if (isError) {
            Text(
                text = errorMessage ?: "",
                color = Color.Red,
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 2.dp, start = 4.dp)
            )
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        onValueChange(selectedDateString)
                        showDatePicker = false
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("موافق")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("إلغاء")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}