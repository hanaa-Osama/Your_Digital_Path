package com.example.yourdigitalpath.presentation.uploadfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestViewModel
import com.example.yourdigitalpath.ui.components.ActionButton
import com.example.yourdigitalpath.ui.components.BackgroundGray
import com.example.yourdigitalpath.ui.components.DarkBlue
import com.example.yourdigitalpath.ui.components.GrayText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploudFilesScreens(
    serviceName: String,
    viewModel: ServiceRequestViewModel,
    onNextClick: () -> Unit,
    onBack: () -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
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
                                text = "الخطوة 3 - المستندات المطلوبة",
                                fontSize = 12.sp,
                                color = GrayText
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                                contentDescription = "Back",
                                tint = DarkBlue
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = BackgroundGray
                    )
                )
            },
            bottomBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BackgroundGray)
                        .padding(16.dp)
                ) {
                    ActionButton(
                        text = "التالي – مراجعة الطلب",
                        /////////////////////////////////////////////هنا يا هنااء///////////////////////////////////////
                        onClick = onNextClick
                    )
                }
            },
            containerColor = BackgroundGray
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                ServiceDataUploadComponent(
                    serviceName = serviceName,
                    viewModel = viewModel
                )
            }
        }
    }
}
