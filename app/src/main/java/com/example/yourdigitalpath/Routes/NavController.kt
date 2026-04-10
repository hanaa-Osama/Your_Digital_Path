package com.example.yourdigitalpath.Routes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberAppNavController(): NavHostController {
    return rememberNavController()
}