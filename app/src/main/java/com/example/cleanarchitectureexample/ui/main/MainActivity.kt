package com.example.cleanarchitectureexample.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.cleanarchitectureexample.navigation.NavRoute
import com.example.cleanarchitectureexample.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = AppTheme.colors.backgroundPrimary
                ) {
                    MainNavigationHandler()
                }
            }
        }
    }

}

sealed class MainRoutes : NavRoute {
    object Root : MainRoutes() {
        override val graphRoute: String = "main_root"
        override val navigationRoute: String = graphRoute
    }

    object Splash : MainRoutes() {
        override val graphRoute: String = "splash"
        override val navigationRoute: String = graphRoute
    }

    object Login : MainRoutes() {
        override val graphRoute: String = "login"
        override val navigationRoute: String = graphRoute
    }

    object Dashboard : MainRoutes() {
        override val graphRoute: String = "dashboard"
        override val navigationRoute: String = graphRoute
    }

    object Settings : MainRoutes() {
        override val graphRoute: String = "settings"
        override val navigationRoute: String = graphRoute
    }

    object Details : MainRoutes() {
        const val CURRENCY_ID = "currencyId"
        private const val base = "details/"
        override val graphRoute: String = "$base{$CURRENCY_ID}"
        override val navigationRoute: String = base

        fun buildFullNavigationRoute(currencyId: String): String {
            return navigationRoute + currencyId
        }
    }

    object Profile : MainRoutes() {
        override val graphRoute: String = "profile"
        override val navigationRoute: String = graphRoute
    }
}

