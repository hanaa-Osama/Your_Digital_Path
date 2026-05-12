package com.example.yourdigitalpath.presentation.Register.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.ui.components.PrimaryBlue

val InputBorder = Color(0xFFE4E8ED)
val HintColor = Color(0xFF9BA3B2)

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
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
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
                Text(text = placeholder, color = HintColor, textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
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
                    Icon(Icons.Outlined.CheckCircle, contentDescription = null, tint = Color(0xFF3A7D5A))
                }
            }
        )
        if (isError && value.isNotEmpty()) {
            Text(text = errorMessage, color = Color(0xFFE24B4A), fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
        }
    }
}