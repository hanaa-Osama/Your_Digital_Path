package com.example.yourdigitalpath.presentation.uploadfile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.domain.model.ServiceRequestModel
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestViewModel
import com.example.yourdigitalpath.presentation.service_request.ServiceTypes
import com.example.yourdigitalpath.ui.components.DarkBlue
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
    val serviceType = viewModel.getServiceType(serviceName)
    val isLost = uiState.selectedType == "بدل فاقد"

    val nationalIdLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { it?.let { uri -> viewModel.uploadNationalId(uri) } }

    val serviceDocLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { it?.let { uri -> viewModel.uploadServiceDocument(uri) } }

    val photoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { it?.let { uri -> viewModel.uploadPersonalPhoto(uri) } }

    val policeLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { it?.let { uri -> viewModel.uploadPoliceReport(uri) } }

    Column(modifier = modifier.fillMaxWidth()) {
        StepperComponent(currentStep = 3)
        Spacer(modifier = Modifier.height(8.dp))

        SectionCard {
            SectionHeader(title = "الملفات المطلوبة")
            Spacer(modifier = Modifier.height(16.dp))

            if (serviceType == ServiceTypes.NATIONAL_ID) {
                NationalIdSection(
                    uiState, isUploading, photoLauncher,
                    nationalIdLauncher, serviceDocLauncher, viewModel
                )
            } else {
                OtherServicesSection(
                    uiState, isUploading, serviceType,
                    nationalIdLauncher, serviceDocLauncher, viewModel
                )
            }

            if (isLost) {
                PoliceReportSection(uiState, isUploading, policeLauncher, viewModel)
            }
        }
    }
}


@Composable
private fun NationalIdSection(
    uiState: ServiceRequestModel?,
    isUploading: Boolean,
    photoLauncher: ActivityResultLauncher<String>,
    nationalIdLauncher: ActivityResultLauncher<String>,
    serviceDocLauncher: ActivityResultLauncher<String>,
    viewModel: ServiceRequestViewModel
) {
    UploadSectionTitle("صورة شخصية حديثة")
    Spacer(modifier = Modifier.height(4.dp))
    WarningBox(
        text = "يجب أن تكون الصورة بخلفية بيضاء وواضحة الملامح",
        isInfo = true
    )
    Spacer(modifier = Modifier.height(8.dp))
    if (uiState?.personalPhotoUrl == null) {
        UploadBox(
            title = "اضغط لرفع الصورة الشخصية",
            subtitle = "JPG / PNG - الحد الأقصى 5 ميجابايت",
            isUploading = isUploading,
            backgroundColor = Color(0xFFFDF5E0),
            borderColor = Color(0xFFD4A843),
            onUploadClick = { photoLauncher.launch("image/*") }
        )
    } else {
        UploadedDocumentItem(
            name = "الصورة الشخصية",
            fileName = uiState.personalPhotoUrl.substringAfterLast("/"),
            onDelete = { viewModel.removePersonalPhoto() }
        )
    }

    if (uiState?.selectedType in listOf("تجديد", "بدل تالف")) {
        Spacer(modifier = Modifier.height(16.dp))
        UploadSectionTitle("البطاقة القومية القديمة (وجه وظهر)")
        Spacer(modifier = Modifier.height(8.dp))
        NationalIdUploadBlock(uiState, isUploading, nationalIdLauncher, viewModel)
    }

    if (uiState?.selectedType == "إصدار لأول مرة") {
        Spacer(modifier = Modifier.height(16.dp))
        UploadSectionTitle("شهادة الميلاد (كمبيوتر)")
        Spacer(modifier = Modifier.height(8.dp))
        if (uiState.serviceDocumentUrl == null) {
            UploadBox(
                title = "اضغط لرفع شهادة الميلاد",
                subtitle = "PNG / JPG / PDF - الحد الأقصى 5 ميجابايت",
                isUploading = isUploading,
                onUploadClick = { serviceDocLauncher.launch("*/*") }
            )
        } else {
            UploadedDocumentItem(
                name = "شهادة الميلاد",
                fileName = uiState.serviceDocumentUrl.substringAfterLast("/"),
                onDelete = { viewModel.removeServiceDocument() }
            )
        }
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
    UploadSectionTitle("صورة البطاقة القومية (وجه وظهر)")
    Spacer(modifier = Modifier.height(8.dp))
    NationalIdUploadBlock(uiState, isUploading, nationalIdLauncher, viewModel)

    val (docTitle, docSubtitle, showDoc) =
        getMainDocConfig(serviceType, uiState.selectedType)

    if (showDoc) {
        Spacer(modifier = Modifier.height(16.dp))
        UploadSectionTitle(docTitle)
        if (docSubtitle.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            WarningBox(text = docSubtitle, isInfo = true)
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (uiState.serviceDocumentUrl == null) {
            UploadBox(
                title = "اضغط لرفع الملف",
                subtitle = "PNG / JPG / PDF - الحد الأقصى 5 ميجابايت",
                isUploading = isUploading,
                onUploadClick = { serviceDocLauncher.launch("*/*") }
            )
        } else {
            UploadedDocumentItem(
                name = docTitle,
                fileName = uiState.serviceDocumentUrl.substringAfterLast("/"),
                onDelete = { viewModel.removeServiceDocument() }
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
    WarningBox("في حالة الفاقد، يجب إرفاق محضر بلاغ من الشرطة")
    Spacer(modifier = Modifier.height(8.dp))
    UploadSectionTitle("محضر الشرطة")
    Spacer(modifier = Modifier.height(8.dp))
    if (uiState.policeReportUrl == null) {
        UploadBox(
            title = "اضغط لرفع محضر الشرطة",
            subtitle = "PNG / JPG / PDF - الحد الأقصى 5 ميجابايت",
            isUploading = isUploading,
            backgroundColor = Color(0xFFFDF5E0),
            borderColor = Color(0xFFD4A843),
            onUploadClick = { policeLauncher.launch("*/*") }
        )
    } else {
        UploadedDocumentItem(
            name = "محضر الشرطة",
            fileName = uiState.policeReportUrl.substringAfterLast("/"),
            onDelete = { viewModel.removePoliceReport() }
        )
    }
}


@Composable
private fun UploadSectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = DarkBlue,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

@Composable
private fun NationalIdUploadBlock(
    uiState: ServiceRequestModel?,
    isUploading: Boolean,
    launcher: ActivityResultLauncher<String>,
    viewModel: ServiceRequestViewModel
) {
    uiState?.nationalIdUrls?.forEachIndexed { index, url ->
        UploadedDocumentItem(
            name = "البطاقة القومية - صورة ${index + 1}",
            fileName = url.substringAfterLast("_"),
            onDelete = { viewModel.removeNationalId(url) }
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
    uiState?.nationalIdUrls?.size?.let {
        if (it < 2) {
            UploadBox(
                title = if (uiState.nationalIdUrls.isEmpty())
                    "اضغط لرفع صورة البطاقة (وجه)"
                else
                    "اضغط لرفع صورة البطاقة (ظهر)",
                subtitle = "تنبيه: يجب رفع صورة البطاقة (وجه وظهر)",
                isUploading = isUploading,
                backgroundColor = Color(0xFFFDF5E0),
                borderColor = Color(0xFFD4A843),
                onUploadClick = { launcher.launch("image/*") }
            )
        }
    }
}

private fun getMainDocConfig(
    serviceType: ServiceTypes,
    selectedType: String
): Triple<String, String, Boolean> = when (serviceType) {
    ServiceTypes.BIRTH_CERTIFICATE ->
        Triple("شهادة الميلاد القديمة", "", true)

    ServiceTypes.MARRIAGE_CERTIFICATE ->
        if (selectedType == "موثقة للسفارة")
            Triple("نسخة من عقد الزواج الأصلي", "", true)
        else
            Triple("عقد الزواج الأصلي (اختياري)", "", selectedType != "بدل فاقد")

    ServiceTypes.DEATH_CERTIFICATE -> when (selectedType) {
        "إصدار لأول مرة" -> Triple(
            "تقرير الوفاة من المستشفى أو الطبيب",
            "يجب أن يكون التقرير موقعاً وختم المستشفى",
            true
        )

        "نسخة إضافية" -> Triple("شهادة الوفاة الأصلية", "", true)
        else -> Triple("", "", false)
    }

    ServiceTypes.DIVORCE_CERTIFICATE -> Triple(
        if (selectedType == "طلاق قضائي") "حكم المحكمة" else "وثيقة الماذون",
        "", true
    )

    ServiceTypes.NATIONAL_ID -> Triple("", "", false)
}