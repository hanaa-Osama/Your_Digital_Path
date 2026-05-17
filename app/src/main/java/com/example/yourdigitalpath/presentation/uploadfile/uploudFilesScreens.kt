package com.example.yourdigitalpath.presentation.uploadfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestViewModel
import com.example.yourdigitalpath.ui.components.ActionButton

@Composable
fun UploudFilesScreens(
    serviceName: String,
    viewModel: ServiceRequestViewModel,
    onNextClick: () -> Unit,
    onBack: () -> Unit
) {
    val isReadyToNext by viewModel.isAllRequiredFilesUploaded.collectAsState()

    Scaffold(
            containerColor = com.example.yourdigitalpath.ui.theme.AppColors.Background,
            bottomBar = {
                if (isReadyToNext) {
                    Box(modifier = Modifier.padding(16.dp)) {
                        ActionButton(
                            text = stringResource(R.string.next),
                            onClick = onNextClick
                        )
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(com.example.yourdigitalpath.ui.theme.AppColors.Background)
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                ServiceDataUploadComponent(
                    serviceName = serviceName,
                    viewModel = viewModel
                )
            }
        }
}