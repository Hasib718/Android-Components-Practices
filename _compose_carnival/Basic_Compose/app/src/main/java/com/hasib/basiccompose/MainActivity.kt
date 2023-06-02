package com.hasib.basiccompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hasib.basiccompose.ui.screens.HomeScreen
import com.hasib.basiccompose.ui.screens.ListScreen
import com.hasib.basiccompose.ui.screens.NumberPadScreen
import com.hasib.basiccompose.ui.theme.BasicComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicComposeTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = HomeScreen.ROUTE_NAME
                ) {
                    composable(HomeScreen.ROUTE_NAME) {
                        HomeScreen(navController = navController)
                    }
                    composable(ListScreen.ROUTE_NAME) {
                        ListScreen(navController = navController)
                    }
                    composable(NumberPadScreen.ROUTE_NAME) {
                        NumberPadScreen(navController = navController)
                    }
                }
            }
        }
    }
}

