package com.example.yourdigitalpath.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.ui.theme.AppStrings

@Composable
fun getServiceTitle(serviceSlug: String): String {
    return when (serviceSlug.trim()) {
        AppStrings.BIRTH_CERTIFICATE -> stringResource(R.string.birth_certificate)
        AppStrings.NATIONAL_ID -> stringResource(R.string.national_id)
        AppStrings.MARRIAGE_CERTIFICATE -> stringResource(R.string.marriage_certificate)
        AppStrings.DEATH_CERTIFICATE -> stringResource(R.string.death_certificate)
        AppStrings.DIVORCE_CERTIFICATE -> stringResource(R.string.divorce_certificate)
        else -> serviceSlug
    }
}

@Composable
fun getLocalizedType(typeSlug: String): String {
    return when (typeSlug.trim()) {
        AppStrings.FULL_COPY -> stringResource(R.string.full_copy)
        AppStrings.SHORT_COPY -> stringResource(R.string.short_copy)
        AppStrings.CERTIFIED_DIGITAL -> stringResource(R.string.certified_digital)
        AppStrings.LOST_REPLACEMENT -> stringResource(R.string.lost_replacement)
        AppStrings.ISSUANCE -> stringResource(R.string.issuance)
        AppStrings.RENEWAL -> stringResource(R.string.renewal)
        AppStrings.OFFICIAL -> stringResource(R.string.official)
        AppStrings.EMBASSY_CERTIFIED -> stringResource(R.string.reason_embassy)
        AppStrings.OFFICIAL_COPIES -> stringResource(R.string.official_copies)
        AppStrings.JUDICIAL -> stringResource(R.string.judicial)
        AppStrings.AUTHORIZED_OFFICER -> stringResource(R.string.authorized_officer)

        AppStrings.OFFICE_PICKUP -> stringResource(R.string.office_pickup)
        AppStrings.DELIVERY -> stringResource(R.string.delivery)
        AppStrings.DIGITAL -> stringResource(R.string.digital)
        AppStrings.HOME_DELIVERY -> stringResource(R.string.home_delivery)
        else -> typeSlug
    }
}

@Composable
fun getLocalizedGovernorate(key: String): String {
    val context = LocalContext.current
    val resId = context.resources.getIdentifier(key, "string", context.packageName)
    return if (resId != 0) stringResource(resId) else key
}
