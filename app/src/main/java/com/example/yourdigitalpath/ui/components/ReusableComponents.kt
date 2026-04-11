package com.example.yourdigitalpath.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

// Color Constants
val PrimaryBlue = Color(0xFF3D5A80)
val DarkBlue = Color(0xFF293241)
val SecondaryBlue = Color(0xFFE0FBFC)
val LightBlue = Color(0xFFEEF4F9)
val GrayText = Color(0xFF98A2B3)
val BackgroundGray = Color(0xFFF9FAFB)
val UnselectedGray = Color(0xFFF2F4F7)

// 1. StepperComponent
@Composable
fun StepperComponent(currentStep: Int) {
    val steps = listOf("النوع", "البيانات", "الملفات", "الدفع")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 16.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        steps.forEachIndexed { index, title ->
            val stepNumber = index + 1
            val isSelected = stepNumber == currentStep
            val isCompleted = stepNumber < currentStep

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f, fill = index != steps.size - 1)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    StepCircle(
                        step = stepNumber,
                        isSelected = isSelected,
                        isCompleted = isCompleted
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = title,
                        color = if (isSelected || isCompleted) DarkBlue else GrayText,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }

                if (index < steps.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 20.dp), // Align with circles
                        color = if (stepNumber < currentStep) PrimaryBlue else Color(0xFFEAECF0),
                        thickness = 2.dp
                    )
                }
            }
        }
    }
}

@Composable
fun StepCircle(step: Int, isSelected: Boolean, isCompleted: Boolean) {
    val backgroundColor = when {
        isCompleted -> PrimaryBlue
        else -> Color.White
    }
    val borderColor = when {
        isCompleted || isSelected -> PrimaryBlue
        else -> Color(0xFFEAECF0)
    }
    val textColor = when {
        isCompleted -> Color.White
        isSelected -> PrimaryBlue
        else -> GrayText
    }

    Box(
        modifier = Modifier
            .size(36.dp)
            .border(if (isSelected) 2.dp else 1.dp, borderColor, CircleShape)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        if (isCompleted) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        } else {
            Text(
                text = step.toString(),
                color = textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// 2. Section Header
@Composable
fun SectionHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(20.dp)
                .background(PrimaryBlue, RoundedCornerShape(2.dp))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = DarkBlue
        )
    }
}

// 3. SelectionChipGroup
@Composable
fun SelectionChipGroup(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    title: String? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        title?.let {
            SectionHeader(it)
            Spacer(modifier = Modifier.height(12.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items.forEach { item ->
                val isSelected = item == selectedItem
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) PrimaryBlue else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .background(
                            color = if (isSelected) LightBlue else UnselectedGray,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { onItemSelected(item) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item,
                        color = if (isSelected) PrimaryBlue else DarkBlue,
                        fontSize = 14.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }
    }
}

// 4. CustomTextField
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    isValid: Boolean = false,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    val isError = errorMessage != null
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = GrayText,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    placeholder,
                    fontSize = 14.sp,
                    color = GrayText.copy(alpha = 0.7f)
                )
            },
            shape = RoundedCornerShape(12.dp),
            leadingIcon = if (isValid) {
                { Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF10B981)) }
            } else leadingIcon?.let {
                { Icon(it, contentDescription = null, tint = GrayText) }
            },
            isError = isError,
            keyboardOptions = keyboardOptions,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = LightBlue,
                unfocusedContainerColor = UnselectedGray,
                focusedBorderColor = PrimaryBlue,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = PrimaryBlue,
                errorBorderColor = Color.Red,
                errorContainerColor = UnselectedGray
            )
        )
        if (isError) {
            Text(
                text = errorMessage ?: "",
                color = Color.Red,
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 2.dp, start = 4.dp)
            )
        }
    }
}

// 5. ActionButton
@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

// 6. Section Card
@Composable
fun SectionCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEAECF0))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start,
            content = content
        )
    }
}

// 7. CustomDatePickerField
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    errorMessage: String? = null
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
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
        Text(
            text = label,
            fontSize = 12.sp,
            color = GrayText,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(UnselectedGray, RoundedCornerShape(12.dp))
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
                    Icon(it, contentDescription = null, tint = GrayText)
                    Spacer(modifier = Modifier.width(12.dp))
                }
                Text(
                    text = value.ifEmpty { placeholder },
                    color = if (value.isEmpty()) GrayText.copy(alpha = 0.7f) else DarkBlue,
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

// 8. CustomDropdown
@Composable
fun CustomDropdown(
    label: String,
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    errorMessage: String? = null
) {
    var expanded by remember { mutableStateOf(false) }
    val isError = errorMessage != null

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = GrayText,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(UnselectedGray, RoundedCornerShape(12.dp))
                .border(
                    1.dp,
                    if (isError) Color.Red else Color.Transparent,
                    RoundedCornerShape(12.dp)
                )
                .clickable { expanded = true }
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = selectedOption,
                    color = if (selectedOption.contains("اختر")) GrayText.copy(alpha = 0.7f) else DarkBlue,
                    fontSize = 14.sp
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = GrayText
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color.White)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
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
}
