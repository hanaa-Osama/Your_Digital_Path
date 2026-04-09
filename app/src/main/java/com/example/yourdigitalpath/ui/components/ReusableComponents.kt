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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Color Constants
val PrimaryBlue = Color(0xFF3D5A80)
val BackgroundGray = Color(0xFFF7F8FA)
val SecondaryBlue = Color(0xFFF0F5FA)
val UnselectedGray = Color(0xFFF0F2F5)

// 1. StepperComponent
@Composable
fun StepperComponent(currentStep: Int) {
    val steps = listOf("النوع", "البيانات", "الملفات", "الدفع")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 16.dp, horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        steps.forEachIndexed { index, title ->
            val stepNumber = index + 1
            val isSelected = stepNumber == currentStep
            val isCompleted = stepNumber < currentStep

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                    // Line behind the circles
                    if (index < steps.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .offset(x = 20.dp) // Adjust based on circle size
                                .fillMaxWidth(0.5f),
                            color = if (stepNumber < currentStep) PrimaryBlue else Color.LightGray.copy(
                                alpha = 0.5f
                            ),
                            thickness = 2.dp
                        )
                    }
                    if (index > 0) {
                        HorizontalDivider(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .offset(x = (-20).dp)
                                .fillMaxWidth(0.5f),
                            color = if (isCompleted || isSelected) PrimaryBlue else Color.LightGray.copy(
                                alpha = 0.5f
                            ),
                            thickness = 2.dp
                        )
                    }

                    StepCircle(
                        step = stepNumber,
                        isSelected = isSelected,
                        isCompleted = isCompleted
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = title,
                    color = if (isSelected) PrimaryBlue else Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun StepCircle(step: Int, isSelected: Boolean, isCompleted: Boolean) {
    val backgroundColor = if (isCompleted) PrimaryBlue else Color.White
    val borderColor =
        if (isSelected || isCompleted) PrimaryBlue else Color.LightGray.copy(alpha = 0.5f)
    val textColor = when {
        isCompleted -> Color.White
        isSelected -> PrimaryBlue
        else -> Color.Gray
    }

    Box(
        modifier = Modifier
            .size(36.dp)
            .border(2.dp, borderColor, CircleShape)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = step.toString(),
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// 2. SelectionChipGroup
@Composable
fun SelectionChipGroup(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    title: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(18.dp)
                    .background(PrimaryBlue, RoundedCornerShape(2.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Responsive grid using FlowRow or chunked rows
        val chunkedItems = items.chunked(2)
        chunkedItems.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { item ->
                    val isSelected = item == selectedItem
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .border(
                                width = 2.dp,
                                color = if (isSelected) PrimaryBlue else Color.Transparent,
                                shape = RoundedCornerShape(14.dp)
                            )
                            .background(
                                color = if (isSelected) SecondaryBlue else UnselectedGray,
                                shape = RoundedCornerShape(14.dp)
                            )
                            .clickable { onItemSelected(item) }
                            .padding(horizontal = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item,
                            color = if (isSelected) PrimaryBlue else Color.Gray,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
                        )
                    }
                }
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

// 3. CustomTextField
@Composable
fun CustomTextField(
    value: String?,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        if (value != null) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(placeholder, fontSize = 14.sp, color = Color.LightGray) },
                shape = RoundedCornerShape(14.dp),
                leadingIcon = leadingIcon?.let {
                    {
                        Icon(
                            it,
                            contentDescription = null,
                            tint = PrimaryBlue
                        )
                    }
                },
                isError = isError,
                keyboardOptions = keyboardOptions,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = UnselectedGray,
                    unfocusedContainerColor = UnselectedGray,
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = PrimaryBlue,
                    errorBorderColor = Color.Red
                )
            )
        }
    }
}

// 4. ActionButton
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
            .height(60.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.White
            )

        }
    }
}

// 5. Section Card
@Composable
fun SectionCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.Start,
            content = content
        )
    }
}

// 6. Custom Dropdown
@Composable
fun CustomDropdown(
    label: String,
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(UnselectedGray, RoundedCornerShape(14.dp))
                    .border(1.dp, PrimaryBlue, RoundedCornerShape(14.dp))
                    .clickable { expanded = true }
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = selectedOption, color = Color.Black, fontSize = 15.sp)
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = PrimaryBlue)
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
                        text = { Text(option) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
