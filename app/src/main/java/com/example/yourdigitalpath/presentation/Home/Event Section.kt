package com.example.yourdigitalpath.presentation.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.blqes.digi.presentation.LastOrdersSection
import com.example.yourdigitalpath.presentation.SearchBar

@Composable
fun EventSection(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }

    val allEvents = getEvents()
    val filteredEvents = remember(searchQuery) {
        if (searchQuery.isBlank()) allEvents
        else allEvents.filter { it.title.contains(searchQuery) }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
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
                        onQueryChange = { searchQuery = it }
                    )
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    Text(
                        text = "الخدمات الرسمية",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                if (filteredEvents.isEmpty()) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(
                            text = "لا توجد نتائج",
                            color = Color(0xFF9BA3B2),
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
                    LastOrdersSection()
                }
            }
        }
    }
}