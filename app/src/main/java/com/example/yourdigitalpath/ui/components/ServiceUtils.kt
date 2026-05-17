package com.example.yourdigitalpath.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.yourdigitalpath.R

@Composable
fun getServiceTitle(serviceSlug: String): String {
    return when (serviceSlug.trim()) {
        "birth_certificate", "شهادة الميلاد", "birth" -> stringResource(R.string.birth_certificate)
        "national_id", "بطاقة الهوية", "identity" -> stringResource(R.string.national_id)
        "marriage_certificate", "شهادة الزواج", "marriage" -> stringResource(R.string.marriage_certificate)
        "death_certificate", "شهادة الوفاة", "death" -> stringResource(R.string.death_certificate)
        "divorce_certificate", "شهادة الطلاق", "divorce" -> stringResource(R.string.divorce_certificate)
        "full_copy", "short_copy", "certified_digital", "lost_replacement",
        "نسخة كاملة", "نسخة مختصرة", "رقمية موثقة", "بدل فاقد" -> ""
        else -> serviceSlug
    }
}

@Composable
fun getLocalizedType(type: String): String {
    return when (type.trim()) {
        "رقمية موثقة", "certified_digital" -> stringResource(R.string.certified_digital)
        "نسخة إضافية", "extra_copy" -> stringResource(R.string.two_copies)
        "موثقة للسفارة", "embassy_certified" -> "Embassy Certified"
        "رقمي", "digital" -> stringResource(R.string.digital)
        "البريد", "delivery" -> stringResource(R.string.delivery)
        "استلام يدوي", "office_pickup" -> stringResource(R.string.office_pickup)
        "Home Delivery" -> stringResource(R.string.home_delivery)
        "full_copy", "نسخة كاملة" -> stringResource(R.string.full_copy)
        "short_copy", "نسخة مختصرة" -> stringResource(R.string.short_copy)
        "lost_replacement", "بدل فاقد" -> stringResource(R.string.lost_replacement)
        else -> type
    }
}