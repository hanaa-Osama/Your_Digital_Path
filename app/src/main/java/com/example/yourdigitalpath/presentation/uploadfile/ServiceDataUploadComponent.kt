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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestViewModel
import com.example.yourdigitalpath.ui.components.SectionCard
import com.example.yourdigitalpath.ui.components.SectionHeader
import com.example.yourdigitalpath.ui.components.StepperComponent
import com.example.yourdigitalpath.ui.theme.AppColors

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
            SectionHeader(
                title = stringResource(R.string.required_files)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.national_id_front_back),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            uiState.nationalIdUrls.forEachIndexed { index, url ->
                UploadedDocumentItem(
                    name = stringResource(
                        R.string.national_id_image_number,
                        index + 1
                    ),
                    fileName = url.substringAfterLast("_"),
                    onDelete = {
                        viewModel.removeNationalId(url)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            if (uiState.nationalIdUrls.size < 2) {
                UploadBox(
                    title =
                        if (uiState.nationalIdUrls.isEmpty())
                            stringResource(R.string.upload_national_id_front)
                        else
                            stringResource(R.string.upload_national_id_back),
                    subtitle = stringResource(
                        R.string.upload_national_id_notice
                    ),
                    isUploading = isUploading,
                    backgroundColor = AppColors.WarningBg,
                    borderColor = AppColors.Warning,
                    onUploadClick = {
                        nationalIdLauncher.launch("image/*")
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            val birthKeyword = stringResource(R.string.birth_keyword)
            val docTitle =
                if (serviceName.contains(birthKeyword)) {
                    stringResource(R.string.old_birth_certificate)
                } else {
                    stringResource(
                        R.string.original_required_document,
                        serviceName
                    )
                }

            Text(
                text = docTitle,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            if (uiState.serviceDocumentUrl == null) {
                UploadBox(
                    title = stringResource(R.string.click_to_upload),
                    subtitle = stringResource(
                        R.string.file_upload_formats
                    ),
                    isUploading = isUploading,
                    backgroundColor = AppColors.PrimaryLight,
                    borderColor = AppColors.PrimaryMid,
                    onUploadClick = {
                        serviceDocLauncher.launch("image/*")
                    }
                )
            } else {
                UploadedDocumentItem(
                    name = docTitle,
                    fileName =
                        uiState.serviceDocumentUrl
                            ?.substringAfterLast("/")
                            ?: "document.jpg",
                    onDelete = {
                        viewModel.removeServiceDocument()
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            WarningBox(
                text = stringResource(
                    R.string.police_report_warning
                )
            )
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
            .background(AppColors.SuccessBg)
            .border(1.dp, AppColors.Success, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.DeleteOutline,
                contentDescription = null,
                tint = AppColors.Danger.copy(alpha = 0.7f)
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
                color = AppColors.Success            )
            Text(
                text = fileName,
                fontSize = 12.sp,
                color = AppColors.TextHint,
                maxLines = 1
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = AppColors.Success,
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
            CircularProgressIndicator(color = AppColors.Primary, modifier = Modifier.size(30.dp))
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CloudUpload,
                    contentDescription = null,
                    tint = if (backgroundColor == Color(0xFFFDF5E0)) Color(0xFFD4A843) else AppColors.Primary,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = if (backgroundColor == Color(0xFFFDF5E0)) Color(0xFF8A6A1F) else AppColors.TextPrimary
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = if (backgroundColor == Color(0xFFFDF5E0)) Color(0xFF8A6A1F) else AppColors.TextHint,
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
            .background(AppColors.WarningBg)
            .border(1.dp, AppColors.Warning, RoundedCornerShape(12.dp))
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
                text = stringResource(R.string.warning),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD4A843)
            )
        }
    }
}
