package com.example.yourdigitalpath.presentation.uploadfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestViewModel
import com.example.yourdigitalpath.ui.components.ActionButton

@Composable
fun UploadFilesScreens(
    serviceName: String,
    viewModel: ServiceRequestViewModel,
    onNextClick: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val isReady = remember(uiState, serviceName) {
        viewModel.isAllRequiredFilesUploaded(serviceName)
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            bottomBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    ActionButton(
                        text = if (isReady) "التالي" else "أكمل رفع الملفات المطلوبة",
                        onClick = {
                            if (isReady) {
                                onNextClick()
                            }
                        }
                    )
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF9FAFB))
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                ServiceDataUploadComponent(
                    serviceName = serviceName,
                    viewModel = viewModel
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}