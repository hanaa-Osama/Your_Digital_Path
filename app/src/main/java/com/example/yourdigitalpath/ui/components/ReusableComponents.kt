package com.example.yourdigitalpath.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.ui.theme.AppColors
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun StepperComponent(currentStep: Int) {
    val steps = listOf(
        stringResource(R.string.step_type),
        stringResource(R.string.step_data),
        stringResource(R.string.step_files),
        stringResource(R.string.step_payment)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.Surface)
            .padding(vertical = 16.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        steps.forEachIndexed { index, title ->
            val stepNumber = index + 1
            val isSelected = stepNumber == currentStep
            val isCompleted = stepNumber < currentStep
            Row(
                verticalAlignment = Alignment.Top,
                modifier = if (index < steps.size - 1) Modifier.weight(1f) else Modifier
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    StepCircle(
                        step = stepNumber,
                        isSelected = isSelected,
                        isCompleted = isCompleted
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = title,
                        color = if (isSelected || isCompleted) AppColors.TextPrimary else AppColors.TextHint,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }

                if (index < steps.size - 1) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(36.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            color = if (stepNumber < currentStep) AppColors.Primary else AppColors.Border,
                            thickness = 2.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StepCircle(step: Int, isSelected: Boolean, isCompleted: Boolean) {
    val backgroundColor = when {
        isCompleted -> AppColors.Primary
        isSelected -> AppColors.Surface
        else -> AppColors.Border
    }
    val borderColor = when {
        isCompleted || isSelected -> AppColors.Primary
        else -> Color.Transparent
    }
    val textColor = when {
        isCompleted -> Color.White
        isSelected -> AppColors.Primary
        else -> AppColors.TextHint
    }

    Box(
        modifier = Modifier
            .size(36.dp)
            .background(backgroundColor, CircleShape)
            .then(
                if (isSelected || isCompleted) Modifier.border(
                    if (isSelected) 2.dp else 1.dp,
                    borderColor,
                    CircleShape
                )
                else Modifier
            )
            .clip(CircleShape),
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
                .background(AppColors.Primary, RoundedCornerShape(2.dp))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = AppColors.TextPrimary
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
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

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items.forEach { item ->
                val isSelected = item == selectedItem
                Box(
                    modifier = Modifier
                        .height(48.dp)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) AppColors.Primary else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .background(
                            color = if (isSelected) AppColors.PrimaryLight else AppColors.Border,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { onItemSelected(item) }
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item,
                        color = if (isSelected) AppColors.Primary else AppColors.TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }
    }
}

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
            color = AppColors.TextHint,
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
                    color = AppColors.TextHint.copy(alpha = 0.7f)
                )
            },
            shape = RoundedCornerShape(12.dp),
            leadingIcon = if (isValid) {
                { Icon(Icons.Default.Check, contentDescription = null, tint = AppColors.Success) }
            } else leadingIcon?.let {
                { Icon(it, contentDescription = null, tint = AppColors.TextHint) }
            },
            isError = isError,
            keyboardOptions = keyboardOptions,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = AppColors.PrimaryLight,
                unfocusedContainerColor = AppColors.Background,
                focusedBorderColor = AppColors.Primary,
                unfocusedBorderColor = AppColors.Border,
                cursorColor = AppColors.Primary,
                errorBorderColor = AppColors.Danger,
                errorContainerColor = AppColors.DangerBg
            )
        )
        if (isError) {
            Text(
                text = errorMessage ?: "",
                color = AppColors.Danger,
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 2.dp, start = 4.dp)
            )
        }
    }
}

@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val layoutDirection = LocalLayoutDirection.current
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = AppColors.Primary)
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
                modifier = Modifier
                    .size(16.dp)
                    .graphicsLayer {
                        rotationY = if (layoutDirection == LayoutDirection.Rtl) 0f else 180f
                    }
            )
        }
    }
}

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
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.Border)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start,
            content = content
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    errorMessage: String? = null
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val isError = errorMessage != null

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.Start
    ) {
        if (label != null) {
            Text(
                text = label,
                fontSize = 12.sp,
                color = AppColors.TextHint,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(AppColors.Background, RoundedCornerShape(12.dp))
                .border(
                    1.dp,
                    if (isError) AppColors.Danger else AppColors.Border,
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
                    Icon(it, contentDescription = null, tint = AppColors.TextHint)
                    Spacer(modifier = Modifier.width(12.dp))
                }
                Text(
                    text = value.ifEmpty { placeholder },
                    color = if (value.isEmpty()) AppColors.TextHint.copy(alpha = 0.7f) else AppColors.TextPrimary,
                    fontSize = 14.sp
                )
            }
        }

        if (isError) {
            Text(
                text = errorMessage ?: "",
                color = AppColors.Danger,
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 2.dp, start = 4.dp)
            )
        }
    }

    if (showDatePicker) {
        val initialDateMillis = remember(value) {
            if (value.isNotEmpty()) {
                try {
                    val format = SimpleDateFormat("yyyy / MM / dd", Locale.getDefault())
                    format.timeZone = TimeZone.getTimeZone("UTC")
                    format.parse(value)?.time
                } catch (e: Exception) {
                    null
                }
            } else null
        }

        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = initialDateMillis
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            val date = Date(it)
                            val format = SimpleDateFormat("yyyy / MM / dd", Locale.getDefault())
                            format.timeZone = TimeZone.getTimeZone("UTC")
                            onValueChange(format.format(date))
                        }
                        showDatePicker = false
                    }
                ) {
                    Text(stringResource(R.string.yes))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

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
            color = AppColors.TextHint,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(AppColors.Background, RoundedCornerShape(12.dp))
                .border(
                    1.dp,
                    if (isError) AppColors.Danger else AppColors.Border,
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
                    color = if (selectedOption.contains(stringResource(R.string.choose))) AppColors.TextHint.copy(alpha = 0.7f) else AppColors.TextPrimary,
                    fontSize = 14.sp
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = AppColors.TextHint
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(AppColors.Surface)
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
                color = AppColors.Danger,
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 2.dp, start = 4.dp)
            )
        }
    }
}
