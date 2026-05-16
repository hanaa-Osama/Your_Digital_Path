package com.example.yourdigitalpath.domain.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.HeartBroken
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.yourdigitalpath.R

data class Event(
    @StringRes val title: Int,
    @StringRes val subtitle: Int,
    val icon: ImageVector,
    @StringRes val price: Int,
    val color: Color,
    val route: String
)

fun eventsList() = listOf(
    Event(
        title = R.string.birth_certificate,
        subtitle = R.string.birth_certificate_subtitle,
        icon = Icons.Outlined.Article,
        price = R.string.egp_20,
        color = Color(0xFFEBF0F7),
        route = "service_request_screen/birth_certificate"
    ),

    Event(
        title = R.string.national_id,
        subtitle = R.string.national_id_subtitle,
        icon = Icons.Outlined.Badge,
        price = R.string.egp_35,
        color = Color(0xFFEBF0F7),
        route = "service_request_screen/national_id"
    ),

    Event(
        title = R.string.marriage_certificate,
        subtitle = R.string.marriage_certificate_subtitle,
        icon = Icons.Outlined.Favorite,
        price = R.string.egp_30,
        color = Color(0xFFEBF0F7),
        route = "service_request_screen/marriage_certificate"
    ),

    Event(
        title = R.string.death_certificate,
        subtitle = R.string.death_certificate_subtitle,
        icon = Icons.Outlined.Description,
        price = R.string.egp_20,
        color = Color(0xFFEBF0F7),
        route = "service_request_screen/death_certificate"
    ),

    Event(
        title = R.string.divorce_certificate,
        subtitle = R.string.divorce_certificate_subtitle,
        icon = Icons.Outlined.HeartBroken,
        price = R.string.egp_30,
        color = Color(0xFFEBF0F7),
        route = "service_request_screen/divorce_certificate"
    )
)