package com.example.yourdigitalpath.presentation.data_entry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.presentation.data_entry.certificates.BirthCertificateViewModel
import com.example.yourdigitalpath.ui.components.ActionButton
import com.example.yourdigitalpath.ui.components.BackgroundGray
import com.example.yourdigitalpath.ui.components.CustomDropdown
import com.example.yourdigitalpath.ui.components.CustomTextField
import com.example.yourdigitalpath.ui.components.DarkBlue
import com.example.yourdigitalpath.ui.components.GrayText
import com.example.yourdigitalpath.ui.components.SectionCard
import com.example.yourdigitalpath.ui.components.SectionHeader
import com.example.yourdigitalpath.ui.components.SelectionChipGroup
import com.example.yourdigitalpath.ui.components.StepperComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataScreen(
    serviceName: String,
    onNext: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BirthCertificateViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val egyptGovernorates = listOf(
        "القاهرة", "الجيزة", "الإسكندرية", "الدقهلية", "البحر الأحمر",
        "البحيرة", "الفيوم", "الغربية", "الإسماعيلية", "المنوفية",
        "المنيا", "القليوبية", "الوادي الجديد", "السويس", "الشرقية",
        "أسوان", "أسيوط", "بني سويف", "بورسعيد", "دمياط",
        "جنوب سيناء", "كفر الشيخ", "مطروح", "الأقصر", "قنا",
        "شمال سيناء", "سوهاج"
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(BackgroundGray)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            TopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = serviceName,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkBlue
                        )
                        Text(
                            text = "حدد نوع الطلب",
                            fontSize = 12.sp,
                            color = GrayText
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBackIos,
                            contentDescription = "Back",
                            tint = DarkBlue
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundGray
                )
            )
            StepperComponent(currentStep = 2)
            Column(modifier = Modifier.padding(16.dp)) {
                SectionCard {
                    SectionHeader("بيانات صاحب الوثيقة")

                    CustomTextField(
                        value = uiState.fullName,
                        onValueChange = { viewModel.updateFullName(it) },
                        label = "الاسم الكامل (عربي فقط)",
                        placeholder = "أحمد محمد علي حسن",
                        isValid = uiState.fullName.trim().split(" ").size >= 4,
                        errorMessage = uiState.fullNameError
                    )

                    CustomTextField(
                        value = uiState.dateOfBirth,
                        onValueChange = { viewModel.updateDateOfBirth(it) },
                        label = "تاريخ الميلاد",
                        placeholder = "1990 / 01 / 15",
                        leadingIcon = Icons.Default.CalendarMonth,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        errorMessage = uiState.dateOfBirthError
                    )

                    CustomDropdown(
                        label = "محافظة الميلاد",
                        selectedOption = uiState.governorate.ifEmpty { "اختر محافظة..." },
                        options = egyptGovernorates,
                        onOptionSelected = { viewModel.updateGovernorate(it) },
                        errorMessage = uiState.governorateError
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                SectionCard {
                    SectionHeader("بيانات مقدم الطلب")

                    CustomTextField(
                        value = uiState.applicantNationalId,
                        onValueChange = { viewModel.updateNationalId(it) },
                        label = "الرقم القومي (14 رقم)",
                        placeholder = "2990115012345XX",
                        isValid = uiState.applicantNationalId.length == 14,
                        errorMessage = uiState.applicantNationalIdError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    CustomTextField(
                        value = uiState.applicantPhone,
                        onValueChange = { viewModel.updatePhone(it) },
                        label = "رقم الهاتف",
                        placeholder = "010XXXXXXXX",
                        leadingIcon = Icons.Default.Phone,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        errorMessage = uiState.applicantPhoneError
                    )

                    SelectionChipGroup(
                        title = "الصفة",
                        items = listOf("صاحب الوثيقة", "ولي الأمر", "وكيل"),
                        selectedItem = uiState.relationship,
                        onItemSelected = { viewModel.updateRelationship(it) }
                    )
                    if (uiState.relationshipError != null) {
                        Text(
                            text = uiState.relationshipError!!,
                            color = Color.Red,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                ActionButton(
                    text = "التالي",
                    onClick = {
                        viewModel.submitForm(onSuccess = onNext)
//                        onNext()
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
