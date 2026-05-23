package com.example.yourdigitalpath.presentation.uploadfile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.geometry.CornerRadius
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
import com.example.yourdigitalpath.ui.theme.AppColors

@Composable
fun ServiceDataUploadComponent(
    serviceName: String,
    viewModel: ServiceRequestViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val isUploading by viewModel.isUploading.collectAsState()
    val config = viewModel.getServiceConfig(serviceName)

    Column(modifier = modifier.fillMaxWidth()) {
        SectionCard {
            SectionHeader(title = stringResource(R.string.required_files))
            Spacer(modifier = Modifier.height(8.dp))

            config?.requiredFiles?.let { requiredFiles ->
                for (req in requiredFiles) {
                    if (req.isRequired(uiState.selectedType)) {
                        DynamicFileSection(
                            requirement = req,
                            urls = uiState.fileUrls[req.id] ?: emptyList(),
                            isUploading = isUploading,
                            onUpload = { uri -> viewModel.uploadFile(req.id, uri, req.maxCount) },
                            onRemove = { url -> viewModel.removeFile(req.id, url) }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun DynamicFileSection(
    requirement: com.example.yourdigitalpath.presentation.service_request.FileRequirement,
    urls: List<String>,
    isUploading: Boolean,
    onUpload: (Uri) -> Unit,
    onRemove: (String) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { it?.let { onUpload(it) } }

    val label = stringResource(requirement.labelRes)

    Column {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            color = AppColors.TextHint,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        if (requirement.descriptionRes != 0) {
            WarningBox(text = stringResource(requirement.descriptionRes), isInfo = true)
            Spacer(modifier = Modifier.height(8.dp))
        }

        for (index in urls.indices) {
            val url = urls[index]
            UploadedDocumentItem(
                name = if (requirement.maxCount > 1) {
                    stringResource(R.string.national_id_image_number, index + 1)
                } else label,
                fileName = url.substringAfterLast("/").substringAfterLast("_"),
                onDelete = { onRemove(url) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (urls.size < requirement.maxCount) {
            UploadBox(
                title = stringResource(R.string.click_to_upload_file, label),
                subtitle = stringResource(R.string.file_formats_limit),
                isUploading = isUploading,
                onUploadClick = { launcher.launch("*/*") }
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
                color = AppColors.Success
            )
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
    backgroundColor: Color = AppColors.PrimaryLight,
    borderColor: Color = AppColors.PrimaryMid,
    onUploadClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
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
                color = AppColors.Primary,
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
                    tint = AppColors.Primary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = AppColors.TextPrimary,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = subtitle,
                    fontSize = 10.sp,
                    color = AppColors.TextHint,
                    textAlign = TextAlign.Center
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
    val bgColor = if (isInfo) AppColors.PrimaryLight else AppColors.WarningBg
    val borderColor = if (isInfo) AppColors.PrimaryMid else AppColors.Warning
    val textColor = if (isInfo) AppColors.Primary else Color(0xFF8A6A1F)
    val iconColor = if (isInfo) AppColors.PrimaryMid else AppColors.Warning

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
        Icon(
            imageVector = Icons.Default.ErrorOutline,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(20.dp)
        )
    }
}
