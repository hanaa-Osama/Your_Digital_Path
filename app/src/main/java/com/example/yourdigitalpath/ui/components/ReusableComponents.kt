package com.example.yourdigitalpath.ui.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SelectableDates
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.ui.theme.AppColors
import com.example.yourdigitalpath.ui.theme.DateUtils
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
            if (value.isNotEmpty()) DateUtils.parseDateFromPicker(value) else null
        }

        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = initialDateMillis,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long) =
                    DateUtils.isDateInPastOrPresent(utcTimeMillis)

                override fun isSelectableYear(year: Int) =
                    DateUtils.isYearInPastOrPresent(year)
            }
        )
        val primary = AppColors.Primary
        val onPrimary = AppColors.PrimaryLight
        val surface = AppColors.Surface
        val onSurface = AppColors.TextPrimary
        val primaryContainer = AppColors.PrimaryMid

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        onValueChange(DateUtils.formatDateForPicker(it))
                    }
                    showDatePicker = false
                }) {
                    Text(stringResource(R.string.yes), color = primary)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(R.string.cancel), color = primary)
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = surface,
                titleContentColor = onSurface,
                headlineContentColor = onSurface,
                weekdayContentColor = AppColors.TextSecond,
                subheadContentColor = onSurface,
                navigationContentColor = primary,
                yearContentColor = onSurface,
                disabledYearContentColor = AppColors.TextHint,
                currentYearContentColor = primary,
                selectedYearContentColor = onPrimary,
                selectedYearContainerColor = primary,
                dayContentColor = onSurface,
                disabledDayContentColor = AppColors.TextHint,
                selectedDayContentColor = onPrimary,
                selectedDayContainerColor = primary,
                todayContentColor = primary,
                todayDateBorderColor = primary,
                dayInSelectionRangeContentColor = onSurface,
                dayInSelectionRangeContainerColor = primaryContainer,
                dividerColor = AppColors.Border
            )
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = surface,
                    titleContentColor = onSurface,
                    headlineContentColor = onSurface,
                    weekdayContentColor = AppColors.TextSecond,
                    subheadContentColor = onSurface,
                    navigationContentColor = primary,
                    yearContentColor = onSurface,
                    disabledYearContentColor = AppColors.TextHint,
                    currentYearContentColor = primary,
                    selectedYearContentColor = onPrimary,
                    selectedYearContainerColor = primary,
                    dayContentColor = onSurface,
                    disabledDayContentColor = AppColors.TextHint,
                    selectedDayContentColor = onPrimary,
                    selectedDayContainerColor = primary,
                    todayContentColor = primary,
                    todayDateBorderColor = primary,
                    dayInSelectionRangeContentColor = onSurface,
                    dayInSelectionRangeContainerColor = primaryContainer,
                    dividerColor = AppColors.Border
                )
            )
        }
    }
}
@Composable
fun CustomDropdown(
    label: String,
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    placeholder: String = "",
    errorMessage: String? = null
) {
    var expanded by remember { mutableStateOf(false) }
    val isError = errorMessage != null
    val primary = AppColors.Primary
    val surface = AppColors.Surface
    val textPrimary = AppColors.TextPrimary

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
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
                    width = if (expanded) 2.dp else 1.dp,
                    color = when {
                        isError -> AppColors.Danger
                        expanded -> primary
                        else -> AppColors.Border
                    },
                    shape = RoundedCornerShape(12.dp)
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
                    text = selectedOption.ifEmpty { placeholder },
                    color = if (selectedOption.isEmpty()) AppColors.TextHint.copy(alpha = 0.7f) else textPrimary,
                    fontSize = 14.sp
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = if (expanded) primary else AppColors.TextHint
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(surface)
            ) {
                options.forEach { option ->
                    val isSelected = option == selectedOption
                    DropdownMenuItem(
                        text = {
                            Text(
                                option,
                                color = if (isSelected) primary else textPrimary,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        },
                        modifier = Modifier.background(
                            if (isSelected) primary.copy(alpha = 0.08f) else Color.Transparent
                        ),
                        colors = MenuDefaults.itemColors(
                            textColor = textPrimary,
                            leadingIconColor = primary,
                        )
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
@Composable
fun SectionCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, AppColors.Border)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            content = content
        )
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = AppColors.Primary,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

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
            .background(AppColors.Primary)
            .padding(vertical = 16.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        steps.forEachIndexed { index, label ->
            val stepNumber = index + 1
            val isActive = stepNumber <= currentStep

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            if (isActive) Color.White else Color.White.copy(alpha = 0.3f),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stepNumber.toString(),
                        color = if (isActive) AppColors.Primary else Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = label,
                    color = if (isActive) Color.White else Color.White.copy(alpha = 0.6f),
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center
                )
            }

            if (index < steps.size - 1) {
                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .height(1.dp)
                        .background(Color.White.copy(alpha = 0.3f))
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectionChipGroup(
    title: String,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.Primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items.forEach { item ->
                val isSelected = item == selectedItem
                val displayLabel = getLocalizedType(item)
                FilterChip(
                    selected = isSelected,
                    onClick = { onItemSelected(item) },
                    label = { Text(displayLabel) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = AppColors.Primary,
                        selectedLabelColor = Color.White,
                        containerColor = AppColors.Background,
                        labelColor = AppColors.TextPrimary
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = AppColors.Border,
                        selectedBorderColor = AppColors.Primary,
                        borderWidth = 1.dp,
                        selectedBorderWidth = 1.dp,
                        enabled = true,
                        selected = isSelected
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }
    }
}

@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.Primary,
            disabledContainerColor = AppColors.Border
        )
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    placeholder: String = "",
    errorMessage: String? = null,
    inputType: InputTypes = InputTypes.DEFAULT,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (label != null) {
            Text(
                text = label,
                fontSize = 13.sp,
                color = AppColors.TextHint,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        OutlinedTextField(
            value = value,
            onValueChange = { newVal ->
                when (inputType) {
                    InputTypes.NATIONAL_ID -> {
                        if (newVal.length <= 14 && newVal.all { it.isDigit() })
                            onValueChange(newVal)
                    }

                    InputTypes.PHONE -> {
                        if (newVal.length <= 11 && newVal.all { it.isDigit() }) {
                            if (newVal.length >= 2 && !newVal.startsWith("01")) {
                                return@OutlinedTextField
                            }
                            onValueChange(newVal)
                        }
                    }

                    else -> onValueChange(newVal)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = AppColors.TextHint.copy(alpha = 0.6f)) },
            isError = errorMessage != null,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppColors.Primary,
                unfocusedBorderColor = AppColors.Border,
                errorBorderColor = AppColors.Danger
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = when (inputType) {
                    InputTypes.NATIONAL_ID, InputTypes.PHONE -> KeyboardType.Number
                    else -> KeyboardType.Text
                }
            )
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = AppColors.Danger,
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            )
        }
    }
}
