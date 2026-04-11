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

@Composable
fun StatusChip(status: OrderStatus) {
    val (label, textColor, bgColor) = when (status) {
        is OrderStatus.Pending    -> Triple("في الانتظار", AppColors.Warning,  AppColors.WarningBg)
        is OrderStatus.InProgress -> Triple("جاري",        AppColors.Warning,  AppColors.WarningBg)
        is OrderStatus.Issued     -> Triple("تم الإصدار",  AppColors.Primary,  AppColors.PrimaryLight)
        is OrderStatus.Completed  -> Triple("مكتمل",       AppColors.Success,  AppColors.SuccessBg)
        is OrderStatus.Rejected   -> Triple("مرفوض",       AppColors.Danger,   AppColors.DangerBg)
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
    StatusChip(status = OrderStatus.Rejected(reason = "غير مقبول"))
}
