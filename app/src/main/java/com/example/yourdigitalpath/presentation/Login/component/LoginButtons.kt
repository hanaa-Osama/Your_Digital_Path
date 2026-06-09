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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.ui.theme.AppColors

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

        Text(
            text = stringResource(R.string.no_account_register),
            color = AppColors.Primary,
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