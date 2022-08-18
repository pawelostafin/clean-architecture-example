package com.example.cleanarchitectureexample.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.cleanarchitectureexample.ui.theme.AppTheme

@Composable
fun SplashScreen(viewModel: SplashViewModel) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.background(
            color = AppTheme.colors.backgroundPrimary
        )
    ) {
        CircularProgressIndicator(
            color = AppTheme.colors.primary
        )
    }

}