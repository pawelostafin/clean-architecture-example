package com.example.cleanarchitectureexample.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.cleanarchitectureexample.ui.base.BaseScreen
import com.example.cleanarchitectureexample.ui.theme.AppTheme

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    navigate: (SplashViewModel.Navigation) -> Unit
) = BaseScreen(
    viewModel = viewModel,
    navigate = navigate
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.backgroundPrimary),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = AppTheme.colors.primary
        )
    }
}