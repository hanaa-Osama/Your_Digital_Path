package com.example.yourdigitalpath.presentation.Home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.yourdigitalpath.R
import com.example.yourdigitalpath.domain.model.eventsList
import com.example.yourdigitalpath.ui.theme.AppColors
import com.example.yourdigitalpath.domain.model.OrderModel

@Composable
fun EventSection(
    navController: NavController,
    ordersList: List<OrderModel> = emptyList()
) {
    var searchQuery by remember { mutableStateOf("") }
    val allEvents = eventsList()
    val filteredEvents = if (searchQuery.isBlank()) {
        allEvents
    } else {
        allEvents.filter { event ->
            val title = stringResource(id = event.title)
            title.contains(
                searchQuery,
                ignoreCase = true
            )
        }
    }
    val configuration = LocalConfiguration.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .padding(vertical = 20.dp, horizontal = 14.dp)
    ) {
            LazyVerticalGrid(
                modifier = Modifier.weight(1f),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    SearchBar(
                        query = searchQuery,
                        onQueryChange = {
                            searchQuery = it
                        }
                    )
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Text(
                        text = stringResource(R.string.official_services),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = AppColors.TextPrimary,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                if (filteredEvents.isEmpty()) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(
                            text = stringResource(R.string.no_results),
                            color = AppColors.TextHint,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                } else {
                    items(filteredEvents) { event ->
                        EventCard(
                            event = event,
                            navController = navController as NavHostController
                        )
                    }
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    LastOrdersSection(ordersList)
                }
            }
    }
}