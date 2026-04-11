package com.example.yourdigitalpath.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.yourdigitalpath.presentation.service_request.ServiceRequestViewModel

@Composable
fun ServiceDataEntryScreen(
    viewModel: ServiceRequestViewModel,
    onNextClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val isUploading by viewModel.isUploading.collectAsState()

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.uploadDocument(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "رفع المستندات المطلوبة",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 16.dp)
        )

        OutlinedCard(
            onClick = { galleryLauncher.launch("image/*") },
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(vertical = 20.dp),
            enabled = !isUploading
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (isUploading) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        if (uiState.documentsUrls.isEmpty()) "اضغط لرفع صورة البطاقة"
                        else "تم الرفع بنجاح ✅"
                    )
                }
            }
        }

        Button(
            onClick = onNextClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            enabled = uiState.documentsUrls.isNotEmpty() && !isUploading
        ) {
            Text("التالي - مراجعة الطلب")
        }
    }
}