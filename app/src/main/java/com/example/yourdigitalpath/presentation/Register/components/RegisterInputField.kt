package com.example.yourdigitalpath.presentation.Register.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.ui.theme.AppColors

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
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    ) {
        Text(text = label, color = AppColors.TextHint, fontSize = 13.sp)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = {
                Text(text = placeholder, color = AppColors.TextHint)
            },
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppColors.Primary,
                unfocusedBorderColor = AppColors.Border,
                errorBorderColor = AppColors.Danger,
                focusedTextColor = AppColors.TextPrimary,
                unfocusedTextColor = AppColors.TextPrimary
            ),
            isError = isError,
            trailingIcon = {
                if (isVerified) {
                    Icon(Icons.Outlined.CheckCircle, contentDescription = null, tint = AppColors.Success)
                }
            }
        )

        if (isError) {
            Text(
                text = errorMessage,
                color = AppColors.Danger,
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            )
        }
    }
}