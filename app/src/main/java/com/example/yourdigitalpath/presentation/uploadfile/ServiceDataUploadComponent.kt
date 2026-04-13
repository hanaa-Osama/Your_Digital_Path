package com.example.yourdigitalpath.presentation.uploadfile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestViewModel
import com.example.yourdigitalpath.ui.components.DarkBlue
import com.example.yourdigitalpath.ui.components.GrayText
import com.example.yourdigitalpath.ui.components.PrimaryBlue
import com.example.yourdigitalpath.ui.components.SectionCard
import com.example.yourdigitalpath.ui.components.SectionHeader
import com.example.yourdigitalpath.ui.components.StepperComponent

@Composable
fun ServiceDataUploadComponent(
    serviceName: String,
    viewModel: ServiceRequestViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val isUploading by viewModel.isUploading.collectAsState()

    val nationalIdLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.uploadNationalId(it) }
    }

    val serviceDocLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.uploadServiceDocument(it) }
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        StepperComponent(currentStep = 3)

        Spacer(modifier = Modifier.height(8.dp))

        SectionCard {
            SectionHeader(title = "الملفات المطلوبة")

            Spacer(modifier = Modifier.height(16.dp))


            if (uiState.nationalIdUrl == null) {
                UploadBox(
                    title = "صورة البطاقة القومية",
                    subtitle = "تنبيه: يجب رفع صورة البطاقة (وجه وظهر)",
                    isUploading = isUploading,
                    backgroundColor = Color(0xFFFDF5E0),
                    borderColor = Color(0xFFD4A843),
                    onUploadClick = { nationalIdLauncher.launch("image/*") }
                )
            } else {
                UploadedDocumentItem(
                    name = "البطاقة القومية – وجه وظهر",
                    fileName = "national_id.jpg",
                    onDelete = { /* نضيف هنا اننا نمسح */ }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))


            val docTitle =
                if (serviceName.contains("ميلاد")) "شهادة الميلاد القديمة" else "أصل المستند المطلوب ($serviceName)"

            if (uiState.serviceDocumentUrl == null) {
                UploadBox(
                    title = docTitle,
                    subtitle = "اضغط لرفع الملف - PDF / JPG / PNG\nالحد الأقصى 5 ميغابايت",
                    isUploading = isUploading,
                    backgroundColor = Color(0xFFEEF4F9),
                    borderColor = Color(0xFF98C1D9),
                    onUploadClick = { serviceDocLauncher.launch("image/*") }
                )
            } else {
                UploadedDocumentItem(
                    name = docTitle,
                    fileName = uiState.serviceDocumentUrl?.substringAfterLast("/")
                        ?: "document.jpg",
                    onDelete = { /*   نضيف هنا اننا نمسح */ }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Warning Box
            WarningBox(text = "في حالة الفاقد، يجب إرفاق محضر بلاغ من الشرطة")
        }
    }
}

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
                tint = GrayText
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
                color = GrayText
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            imageVector = Icons.Default.CheckCircle,
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
            .height(140.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable(enabled = !isUploading) { onUploadClick() },
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
            drawRoundRect(
                color = borderColor,
                style = Stroke(
                    width = 2f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                ),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(12.dp.toPx())
            )
        }

        if (isUploading) {
            CircularProgressIndicator(color = PrimaryBlue)
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CloudUpload,
                    contentDescription = null,
                    tint = if (backgroundColor == Color(0xFFFDF5E0)) Color(0xFFD4A843) else PrimaryBlue,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = if (backgroundColor == Color(0xFFFDF5E0)) Color(0xFF8A6A1F) else DarkBlue
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = if (backgroundColor == Color(0xFFFDF5E0)) Color(0xFF8A6A1F) else GrayText,
                    textAlign = TextAlign.Center,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun WarningBox(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFFDF5E0))
            .border(1.dp, Color(0xFFD4A843), RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = Color(0xFF8A6A1F),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.ErrorOutline,
                contentDescription = null,
                tint = Color(0xFFD4A843),
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "تنبيه",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD4A843)
            )
        }
    }
}
