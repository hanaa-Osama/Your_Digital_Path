package com.example.yourdigitalpath.presentation.uploadfile

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.ui.components.DarkBlue
import com.example.yourdigitalpath.ui.components.GrayText
import com.example.yourdigitalpath.ui.components.PrimaryBlue

@Composable
fun UploadedDocumentItem(
    name: String,
    fileName: String,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFEAF4EE))
            .border(1.dp, Color(0xFF3A7D5A), RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.DeleteOutline,
                contentDescription = null,
                tint = Color.Red.copy(alpha = 0.7f)
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color(0xFF3A7D5A)
            )
            Text(
                text = fileName,
                fontSize = 12.sp,
                color = GrayText,
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Default.CheckCircleOutline,
            contentDescription = null,
            tint = Color(0xFF3A7D5A),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun UploadBox(
    title: String,
    subtitle: String,
    isUploading: Boolean,
    backgroundColor: Color = Color(0xFFEEF4F9),
    borderColor: Color = Color(0xFF98C1D9),
    onUploadClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable(enabled = !isUploading) { onUploadClick() },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRoundRect(
                color = borderColor,
                style = Stroke(
                    width = 2f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                ),
                cornerRadius = CornerRadius(12.dp.toPx())
            )
        }
        if (isUploading) {
            CircularProgressIndicator(
                color = PrimaryBlue,
                modifier = Modifier.size(30.dp)
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CloudUpload,
                    contentDescription = null,
                    tint = if (backgroundColor == Color(0xFFFDF5E0))
                        Color(0xFFD4A843) else PrimaryBlue,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = if (backgroundColor == Color(0xFFFDF5E0))
                        Color(0xFF8A6A1F) else DarkBlue
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = if (backgroundColor == Color(0xFFFDF5E0))
                        Color(0xFF8A6A1F) else GrayText,
                    textAlign = TextAlign.Center,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun WarningBox(
    text: String,
    isInfo: Boolean = false
) {
    val bgColor = if (isInfo) Color(0xFFEEF4F9) else Color(0xFFFDF5E0)
    val borderColor = if (isInfo) Color(0xFF98C1D9) else Color(0xFFD4A843)
    val textColor = if (isInfo) Color(0xFF3D5A80) else Color(0xFF8A6A1F)
    val iconColor = if (isInfo) Color(0xFF98C1D9) else Color(0xFFD4A843)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = textColor,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.ErrorOutline,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "تنبيه",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = iconColor
            )
        }
    }
}