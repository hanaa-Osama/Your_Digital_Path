package com.example.yourdigitalpath.presentation.orders_history.order_component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourdigitalpath.domain.model.OrderStatus
import com.example.yourdigitalpath.ui.theme.AppColors
import androidx.compose.ui.res.stringResource
import com.example.yourdigitalpath.R

@Composable
fun StatusChip(status: OrderStatus) {
    val (label, textColor, bgColor) = when (status) {
        is OrderStatus.Pending -> Triple(
            stringResource(R.string.status_pending),
            AppColors.Warning,
            AppColors.WarningBg
        )
        is OrderStatus.InProgress -> Triple(
            stringResource(R.string.status_in_progress),
            AppColors.Warning,
            AppColors.WarningBg
        )
        is OrderStatus.Issued -> Triple(
            stringResource(R.string.status_issued),
            AppColors.Primary,
            AppColors.PrimaryLight
        )
        is OrderStatus.Completed -> Triple(
            stringResource(R.string.status_completed),
            AppColors.Success,
            AppColors.SuccessBg
        )
        is OrderStatus.Rejected -> Triple(
            stringResource(R.string.status_rejected),
            AppColors.Danger,
            AppColors.DangerBg
        )
    }

    Text(
        text = label,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = textColor,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}

@Preview
@Composable
fun PreviewStatusChip() {
    StatusChip(status = OrderStatus.Pending)
}
@Preview
@Composable
fun PreviewStatusChip1() {
    StatusChip(status = OrderStatus.InProgress)
}
@Preview
@Composable
fun PreviewStatusChip2() {
    StatusChip(status = OrderStatus.Issued)
}
@Preview
@Composable
fun PreviewStatusChip3() {
    StatusChip(status = OrderStatus.Completed)
}
@Preview
@Composable
fun PreviewStatusChip4() {
    StatusChip(
        status = OrderStatus.Rejected(
            reason = stringResource(R.string.not_accepted)
        )
    )
}
