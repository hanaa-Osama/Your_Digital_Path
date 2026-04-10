package com.example.yourdigitalpath.presentation.Home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.blqes.digi.presentation.BottomNavBar
import com.blqes.digi.presentation.HeaderSection


@Composable
fun MainScreen(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .background(
                Color(0XFF3D5A80)
            )


    ) {

        HeaderSection()

        Spacer(modifier = Modifier.height(16.dp))



        Column(
            modifier = Modifier.weight(1f)
        ) {

            EventSection(navController)
        }
        BottomNavBar()


    }
}

@Composable
@Preview
private fun MainScreenprev() {
    MainScreen(
        navController = androidx.navigation.compose.rememberNavController()
    )
}


