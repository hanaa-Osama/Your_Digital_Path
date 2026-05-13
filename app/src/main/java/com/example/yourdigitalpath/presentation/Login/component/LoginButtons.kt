package com.blqes.digi.Login

import com.example.yourdigitalpath.presentation.Login.component.PrimaryButton

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.yourdigitalpath.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginButtons(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {

    Column {

        PrimaryButton(
            text = stringResource(R.string.login),
            onClick = onLoginClick
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = stringResource(R.string.login_with_national_id),
                color = Color(0xFF3D5A80)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.no_account_register),
            color = Color(0xFF3D5A80),
            fontSize = 12.sp,
            modifier = Modifier.clickable {
                onRegisterClick()
            }
        )
    }
}

@Composable
@Preview
private fun LoginButtonsprev() {

    LoginButtons(
        onLoginClick = {},
        onRegisterClick = {}
    )
}