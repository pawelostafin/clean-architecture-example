package com.example.cleanarchitectureexample.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun SplashScreen(viewModel: SplashViewModel) {

    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }

}