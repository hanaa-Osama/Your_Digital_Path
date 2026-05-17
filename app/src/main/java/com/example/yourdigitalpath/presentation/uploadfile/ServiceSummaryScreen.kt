package com.example.yourdigitalpath.presentation.uploadfile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.presentation.data_entry.certificates.BirthCertificateViewModel
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestViewModel
import com.example.yourdigitalpath.ui.theme.AppColors
import com.example.yourdigitalpath.ui.theme.LocalDarkTheme
import com.example.yourdigitalpath.ui.components.ActionButton
import com.example.yourdigitalpath.ui.components.getServiceTitle
import com.example.yourdigitalpath.ui.components.getLocalizedType

@Composable
fun ServiceSummaryScreen(
    serviceName: String,
    serviceRequestViewModel: ServiceRequestViewModel,
    birthCertificateViewModel: BirthCertificateViewModel = hiltViewModel(),
    onConfirm: () -> Unit
) {
    val requestState by serviceRequestViewModel.uiState.collectAsState()
    val personalState by birthCertificateViewModel.uiState.collectAsState()
    val localizedServiceName = getServiceTitle(serviceName)

    Scaffold(
        containerColor = AppColors.Background,
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppColors.Surface)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ActionButton(
                    text = stringResource(R.string.confirm_and_pay),
                    onClick = onConfirm
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.confirm_data_notice),
                    fontSize = 12.sp,
                    color = AppColors.TextHint
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.Background)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = if (LocalDarkTheme.current)
                                listOf(
                                    Color(0xFF1D2A44),
                                    Color(0xFF0F1929)
                                )
                            else
                                listOf(
                                    AppColors.Primary,
                                    Color(0xFF293241)
                                )
                        )
                    )
                    .padding(24.dp)
            ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White.copy(alpha = 0.2f))
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.review_request),
                                color = Color.White,
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Icon(
                                Icons.Default.AssignmentTurnedIn,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = stringResource(R.string.order_summary),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = stringResource(R.string.review_data_before_payment),
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
                Column(modifier = Modifier.padding(16.dp)) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            AppColors.Border
                        )
                    ) {
                        Column {
                            SummaryRow(
                                label = stringResource(R.string.service),
                                value = localizedServiceName
                            )
                            HorizontalDivider(color = AppColors.Border)
                            SummaryRow(
                                label = stringResource(R.string.request_type),
                                value = if (requestState.selectedType.isNotEmpty()) {
                                    getLocalizedType(requestState.selectedType)
                                } else {
                                    stringResource(R.string.not_specified)
                                }
                            )
                            HorizontalDivider(color = AppColors.Border)
                            SummaryRow(
                                label = stringResource(R.string.document_owner_name),
                                value = personalState.fullName.ifEmpty {
                                    stringResource(R.string.not_available)
                                }
                            )
                            HorizontalDivider(color = AppColors.Border)
                            SummaryRow(
                                label = stringResource(R.string.national_id),
                                value = personalState.applicantNationalId.ifEmpty {
                                    stringResource(R.string.not_available)
                                }
                            )
                            HorizontalDivider(color = AppColors.Border)
                            SummaryRow(
                                label = stringResource(R.string.applicant),
                                value = personalState.relationship.ifEmpty {
                                    stringResource(R.string.document_owner)
                                }
                            )
                            HorizontalDivider(color = AppColors.Border)
                            SummaryRow(
                                label = stringResource(R.string.copies_label),
                                value = when (requestState.copiesCount) {
                                    1 -> stringResource(R.string.one_copy)
                                    2 -> stringResource(R.string.two_copies)
                                    else -> stringResource(
                                        R.string.copies_count1,
                                        requestState.copiesCount
                                    )
                                }
                            )
                            HorizontalDivider(color = AppColors.Border)
                            SummaryRow(
                                label = stringResource(R.string.delivery_method),
                                value = if (requestState.deliveryMethod.isNotEmpty()) {
                                    getLocalizedType(requestState.deliveryMethod)
                                } else {
                                    stringResource(R.string.home_delivery)
                                }
                            )
                            HorizontalDivider(color = AppColors.Border)
                            SummaryRow(
                                label = stringResource(R.string.processing_time),
                                value = stringResource(R.string.processing_days),
                                valueColor = AppColors.Success
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            AppColors.Border
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = stringResource(R.string.uploaded_documents),
                                fontWeight = FontWeight.Bold,
                                color = AppColors.TextPrimary
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            requestState.nationalIdUrls.forEachIndexed { index, _ ->
                                DocumentCheckItem(
                                    name = stringResource(
                                        R.string.national_id_image_number,
                                        index + 1
                                    ),
                                    count = 1
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                            }
                            requestState.serviceDocumentUrl?.let {
                                val docName =
                                    if (serviceName.contains(stringResource(R.string.birth_keyword))) {
                                        stringResource(R.string.old_birth_certificate)
                                    } else {
                                        stringResource(R.string.original_required_document)
                                    }
                                DocumentCheckItem(
                                    name = docName,
                                    count = 1
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = AppColors.PrimaryLight
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            AppColors.PrimaryMid
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(horizontalAlignment = Alignment.Start) {
                                Text(
                                    text = stringResource(
                                        R.string.total_price,
                                        requestState.totalFees.toInt()
                                    ),
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AppColors.Primary
                                )
                                Text(
                                    text = stringResource(R.string.egp),
                                    fontSize = 12.sp,
                                    color = AppColors.TextHint
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = stringResource(R.string.total_fees),
                                    fontWeight = FontWeight.Bold,
                                    color = AppColors.TextPrimary
                                )
                                Text(
                                    text = stringResource(
                                        R.string.copies_price,
                                        requestState.copiesCount
                                    ),
                                    fontSize = 12.sp,
                                    color = AppColors.TextSecond
                                )
                            }
                        }
                    }
                }
            }
        }
}

@Composable
fun SummaryRow(
    label: String,
    value: String,
    valueColor: Color = AppColors.TextPrimary
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = AppColors.TextSecond,
            fontSize = 14.sp
        )
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            color = valueColor,
            fontSize = 14.sp
        )
    }
}

@Composable
fun DocumentCheckItem(
    name: String,
    count: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(AppColors.SuccessBg, CircleShape)
                .border(1.dp, AppColors.Success, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Check,
                contentDescription = null,
                tint = AppColors.Success,
                modifier = Modifier.size(14.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = name,
            color = AppColors.TextSecond,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = stringResource(R.string.document_count, count),
            color = AppColors.Primary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
