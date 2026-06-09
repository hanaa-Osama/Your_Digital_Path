package com.example.yourdigitalpath.presentation.uploadfile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
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
import androidx.compose.material.icons.filled.CheckCircleOutline
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
import com.example.yourdigitalpath.domain.model.ServiceRequestModel
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestViewModel
import com.example.yourdigitalpath.presentation.service_request.ServiceTypes
import com.example.yourdigitalpath.ui.components.SectionCard
import com.example.yourdigitalpath.ui.components.SectionHeader
import com.example.yourdigitalpath.ui.components.StepperComponent
import com.example.yourdigitalpath.ui.theme.AppColors
import com.example.yourdigitalpath.ui.theme.AppStrings
@Composable
fun ServiceDataUploadComponent(
    serviceName: String,
    viewModel: ServiceRequestViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val isUploading by viewModel.isUploading.collectAsState()
    val serviceType = viewModel.getServiceType(serviceName)
    val isLost = uiState.selectedType == AppStrings.LOST_REPLACEMENT

    val nationalIdLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { it?.let { uri -> viewModel.uploadFile("national_id", uri, 2) } }

    val serviceDocLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { it?.let { uri -> viewModel.uploadFile("service_doc", uri, 1) } }

    val photoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { it?.let { uri -> viewModel.uploadFile("personal_photo", uri, 1) } }

    val policeLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { it?.let { uri -> viewModel.uploadFile("police_report", uri, 1) } }

    Column(modifier = modifier.fillMaxWidth()) {
        StepperComponent(currentStep = 3)
        Spacer(modifier = Modifier.height(8.dp))

        SectionCard {
            SectionHeader(title = stringResource(R.string.required_files))
            Spacer(modifier = Modifier.height(16.dp))

            if (serviceType == ServiceTypes.NATIONAL_ID) {
                NationalIdSection(
                    uiState = uiState,
                    isUploading = isUploading,
                    photoLauncher = photoLauncher,
                    nationalIdLauncher = nationalIdLauncher,
                    serviceDocLauncher = serviceDocLauncher,
                    policeLauncher = policeLauncher,
                    viewModel = viewModel
                )
            } else {
                OtherServicesSection(
                    uiState = uiState,
                    isUploading = isUploading,
                    serviceType = serviceType,
                    nationalIdLauncher = nationalIdLauncher,
                    serviceDocLauncher = serviceDocLauncher,
                    viewModel = viewModel
                )
                if (isLost) {
                    PoliceReportSection(
                        uiState = uiState,
                        isUploading = isUploading,
                        policeLauncher = policeLauncher,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
@Composable
private fun NationalIdSection(
    uiState: ServiceRequestModel,
    isUploading: Boolean,
    photoLauncher: ActivityResultLauncher<String>,
    nationalIdLauncher: ActivityResultLauncher<String>,
    serviceDocLauncher: ActivityResultLauncher<String>,
    policeLauncher: ActivityResultLauncher<String>,
    viewModel: ServiceRequestViewModel
) {
    UploadSectionTitle(stringResource(R.string.personal_photo_label))
    Spacer(modifier = Modifier.height(4.dp))
    WarningBox(text = stringResource(R.string.personal_photo_notice), isInfo = true)
    Spacer(modifier = Modifier.height(8.dp))

    val photoUrls = uiState.fileUrls["personal_photo"] ?: emptyList()
    if (photoUrls.isEmpty()) {
        UploadBox(
            title = stringResource(R.string.upload_personal_photo),
            subtitle = stringResource(R.string.file_formats_img),
            isUploading = isUploading,
            onUploadClick = { photoLauncher.launch("image/*") }
        )
    } else {
        UploadedDocumentItem(
            name = stringResource(R.string.personal_photo_label),
            fileName = photoUrls.first().substringAfterLast("/"),
            onDelete = { viewModel.removeFile("personal_photo", photoUrls.first()) }
        )
    }
    if (uiState.selectedType == AppStrings.RENEWAL ||
        uiState.selectedType == AppStrings.DAMAGED_REPLACEMENT
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        UploadSectionTitle(stringResource(R.string.old_national_id_label))
        Spacer(modifier = Modifier.height(8.dp))
        NationalIdUploadBlock(
            urls = uiState.fileUrls["national_id"] ?: emptyList(),
            isUploading = isUploading,
            launcher = nationalIdLauncher,
            onRemove = { url -> viewModel.removeFile("national_id", url) }
        )
    }
    if (uiState.selectedType == AppStrings.ISSUANCE) {
        Spacer(modifier = Modifier.height(16.dp))
        UploadSectionTitle(stringResource(R.string.original_birth_certificate_label))
        Spacer(modifier = Modifier.height(8.dp))
        val birthCertUrls = uiState.fileUrls["service_doc"] ?: emptyList()
        if (birthCertUrls.isEmpty()) {
            UploadBox(
                title = stringResource(R.string.upload_birth_certificate),
                subtitle = stringResource(R.string.file_formats_all),
                isUploading = isUploading,
                onUploadClick = { serviceDocLauncher.launch("*/*") }
            )
        } else {
            UploadedDocumentItem(
                name = stringResource(R.string.original_birth_certificate_label),
                fileName = birthCertUrls.first().substringAfterLast("/"),
                onDelete = { viewModel.removeFile("service_doc", birthCertUrls.first()) }
            )
        }
    }
    if (uiState.selectedType == AppStrings.LOST_REPLACEMENT) {
        Spacer(modifier = Modifier.height(16.dp))
        PoliceReportSection(
            uiState = uiState,
            isUploading = isUploading,
            policeLauncher = policeLauncher,
            viewModel = viewModel
        )
    }

    Spacer(modifier = Modifier.height(16.dp))
}
@Composable
private fun OtherServicesSection(
    uiState: ServiceRequestModel,
    isUploading: Boolean,
    serviceType: ServiceTypes,
    nationalIdLauncher: ActivityResultLauncher<String>,
    serviceDocLauncher: ActivityResultLauncher<String>,
    viewModel: ServiceRequestViewModel
) {
    UploadSectionTitle(stringResource(R.string.national_id_front_back))
    Spacer(modifier = Modifier.height(8.dp))
    NationalIdUploadBlock(
        urls = uiState.fileUrls["national_id"] ?: emptyList(),
        isUploading = isUploading,
        launcher = nationalIdLauncher,
        onRemove = { url -> viewModel.removeFile("national_id", url) }
    )

    val (docTitle, docNotice, showDoc) = getMainDocConfig(serviceType, uiState.selectedType)

    if (showDoc) {
        Spacer(modifier = Modifier.height(16.dp))
        UploadSectionTitle(docTitle)
        if (docNotice.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            WarningBox(text = docNotice, isInfo = true)
        }
        Spacer(modifier = Modifier.height(8.dp))
        val docUrls = uiState.fileUrls["service_doc"] ?: emptyList()
        if (docUrls.isEmpty()) {
            UploadBox(
                title = stringResource(R.string.click_to_upload),
                subtitle = stringResource(R.string.file_formats_all),
                isUploading = isUploading,
                onUploadClick = { serviceDocLauncher.launch("*/*") }
            )
        } else {
            UploadedDocumentItem(
                name = docTitle,
                fileName = docUrls.first().substringAfterLast("/"),
                onDelete = { viewModel.removeFile("service_doc", docUrls.first()) }
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}
@Composable
private fun PoliceReportSection(
    uiState: ServiceRequestModel,
    isUploading: Boolean,
    policeLauncher: ActivityResultLauncher<String>,
    viewModel: ServiceRequestViewModel
) {
    WarningBox(stringResource(R.string.police_report_notice))
    Spacer(modifier = Modifier.height(8.dp))
    UploadSectionTitle(stringResource(R.string.police_report_label))
    Spacer(modifier = Modifier.height(8.dp))
    val policeUrls = uiState.fileUrls["police_report"] ?: emptyList()
    if (policeUrls.isEmpty()) {
        UploadBox(
            title = stringResource(R.string.upload_police_report),
            subtitle = stringResource(R.string.file_formats_all),
            isUploading = isUploading,
            onUploadClick = { policeLauncher.launch("*/*") }
        )
    } else {
        UploadedDocumentItem(
            name = stringResource(R.string.police_report_label),
            fileName = policeUrls.first().substringAfterLast("/"),
            onDelete = { viewModel.removeFile("police_report", policeUrls.first()) }
        )
    }
}
@Composable
private fun NationalIdUploadBlock(
    urls: List<String>,
    isUploading: Boolean,
    launcher: ActivityResultLauncher<String>,
    onRemove: (String) -> Unit
) {
    urls.forEachIndexed { index, url ->
        UploadedDocumentItem(
            name = stringResource(R.string.national_id_image_number, index + 1),
            fileName = url.substringAfterLast("_"),
            onDelete = { onRemove(url) }
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
    if (urls.size < 2) {
        UploadBox(
            title = if (urls.isEmpty())
                stringResource(R.string.upload_national_id_front)
            else
                stringResource(R.string.upload_national_id_back),
            subtitle = stringResource(R.string.upload_national_id_notice),
            isUploading = isUploading,
            onUploadClick = { launcher.launch("image/*") }
        )
    }
}

@Composable
private fun getMainDocConfig(
    serviceType: ServiceTypes,
    selectedType: String
): Triple<String, String, Boolean> = when (serviceType) {

    ServiceTypes.BIRTH_CERTIFICATE -> Triple(
        stringResource(R.string.old_birth_certificate),
        "",
        selectedType != AppStrings.LOST_REPLACEMENT
    )

    ServiceTypes.MARRIAGE_CERTIFICATE ->
        if (selectedType == AppStrings.EMBASSY_CERTIFIED)
            Triple(stringResource(R.string.original_contract_embassy_label), "", true)
        else
            Triple(
                stringResource(R.string.marriage_contract_label),
                "",
                selectedType != AppStrings.LOST_REPLACEMENT
            )

    ServiceTypes.DEATH_CERTIFICATE -> when (selectedType) {
        AppStrings.ISSUANCE -> Triple(
            stringResource(R.string.death_report_label),
            stringResource(R.string.death_report_notice),
            true
        )

        AppStrings.ADDITIONAL_COPY -> Triple(
            stringResource(R.string.original_death_cert_label),
            "",
            true
        )

        else -> Triple("", "", false)
    }

    ServiceTypes.DIVORCE_CERTIFICATE -> Triple(
        if (selectedType == AppStrings.JUDICIAL)
            stringResource(R.string.divorce_doc_label)
        else
            stringResource(R.string.authorized_officer_doc_label),
        "",
        true
    )

    ServiceTypes.NATIONAL_ID -> Triple("", "", false)
}


@Composable
private fun UploadSectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = AppColors.Primary,
        modifier = Modifier.padding(bottom = 4.dp)
    )
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
            imageVector = Icons.Default.CheckCircleOutline,
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
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = AppColors.TextPrimary,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = AppColors.TextHint,
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
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.ErrorOutline,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = stringResource(R.string.warning),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = iconColor
            )
        }
    }
}
