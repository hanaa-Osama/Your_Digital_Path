package com.blqes.digi.Login

import PrimaryButton
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable

fun LoginButtons(
    onLoginClick: () -> Unit
) {
    Column {
        PrimaryButton(
            text = "تسجيل الدخول",
            onClick = onLoginClick
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("الدخول بالبطاقة القومية", color = Color(0xFF3D5A80))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "ليس لديك حساب؟ سجل الآن",
            color = Color(0xFF3D5A80),
            fontSize = 12.sp
        )
    }
}

@Composable
@Preview
private fun LoginButtonsprev() {
    LoginButtons(
        onLoginClick = {}
    )

}