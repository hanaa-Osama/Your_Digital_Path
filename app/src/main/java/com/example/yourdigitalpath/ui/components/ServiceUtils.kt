package com.example.yourdigitalpath.ui.components

import androidx.compose.runtime.Composable
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
        AppStrings.EMBASSY_CERTIFIED -> stringResource(R.string.embassy_certified)
        AppStrings.OFFICIAL_COPIES -> stringResource(R.string.official_copies)
        AppStrings.JUDICIAL -> stringResource(R.string.judicial)
        AppStrings.AUTHORIZED_OFFICER -> stringResource(R.string.authorized_officer)
        AppStrings.DAMAGED_REPLACEMENT -> stringResource(R.string.damaged_replacement)
        AppStrings.KHULA -> stringResource(R.string.khul)
        AppStrings.ADDITIONAL_COPY -> stringResource(R.string.additional_copy)

        AppStrings.OFFICE_PICKUP -> stringResource(R.string.office_pickup)
        AppStrings.DELIVERY -> stringResource(R.string.delivery)
        AppStrings.DIGITAL -> stringResource(R.string.digital)
        AppStrings.HOME_DELIVERY -> stringResource(R.string.home_delivery)

        AppStrings.REASON_EXPIRY -> stringResource(R.string.reason_expiry)
        AppStrings.REASON_LOST -> stringResource(R.string.reason_lost)
        AppStrings.REASON_DAMAGED -> stringResource(R.string.reason_damaged)
        AppStrings.REASON_CHANGE_DATA -> stringResource(R.string.reason_change_data)
        AppStrings.REASON_INHERITANCE -> stringResource(R.string.reason_inheritance)
        AppStrings.REASON_INSURANCE -> stringResource(R.string.reason_insurance)
        AppStrings.REASON_EMBASSY -> stringResource(R.string.reason_embassy)
        AppStrings.REASON_LEGAL -> stringResource(R.string.reason_legal)
        AppStrings.REASON_TRAVEL -> stringResource(R.string.reason_travel)
        AppStrings.REASON_REMARRIAGE -> stringResource(R.string.reason_remarriage)
        AppStrings.REASON_RESIDENCY -> stringResource(R.string.reason_residency)
        AppStrings.REASON_WORK -> stringResource(R.string.reason_work)
        AppStrings.REASON_RENEWAL -> stringResource(R.string.reason_renewal)

        else -> typeSlug
    }
}

