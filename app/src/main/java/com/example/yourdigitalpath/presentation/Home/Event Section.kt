package com.example.yourdigitalpath.presentation.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.blqes.digi.presentation.EventCard
import com.blqes.digi.presentation.LastOrdersSection
import com.blqes.digi.presentation.SearchBar
import com.blqes.digi.presentation.getEvents

@Composable
fun EventSection(navController: NavController) {

    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                //.padding(vertical = 25.dp, horizontal = 20.dp)

                .background(color = Color.White)
                .padding(vertical = 20.dp, horizontal = 14.dp)

        )
        {


            LazyVerticalGrid(
                modifier = Modifier.weight(1f),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    SearchBar()
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Text(
                        "الخدمات الرسمية",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
                items(getEvents()) { event ->
                    EventCard(
                        event = event, price = "20egp",
                        navController = navController
                    )

                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    LastOrdersSection()
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


        }

    }
}

@Composable
@Preview
private fun EventSectionprev() {
    EventSection(navController = androidx.navigation.compose.rememberNavController())
}