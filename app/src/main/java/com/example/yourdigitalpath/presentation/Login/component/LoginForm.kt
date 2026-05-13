package com.example.yourdigitalpath.presentation.Login.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.yourdigitalpath.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginForm(
    phone: String,
    onPhoneChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit
) {
    Column {
        Text(
            text = stringResource(
                R.string.phone_or_national_id
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            value = phone,
            onValueChange = onPhoneChange,
            hint = stringResource(
                R.string.national_id_hint
            ),
            isNationalId = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(
                R.string.password
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            value = password,
            onValueChange = onPasswordChange,
            hint = stringResource(
                R.string.password
            ),
            isPassword = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(
                R.string.forgot_password
            ),
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}