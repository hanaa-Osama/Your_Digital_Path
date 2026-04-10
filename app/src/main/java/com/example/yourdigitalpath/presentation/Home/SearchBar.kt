package com.blqes.digi.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar() {
    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("ابحث عن خدمة...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null)
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(12.dp)
                ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFE3E3E5),
                unfocusedContainerColor = Color(0xFFf7F8FA),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
@Preview
private fun SearchBarprev() {
    SearchBar()
}