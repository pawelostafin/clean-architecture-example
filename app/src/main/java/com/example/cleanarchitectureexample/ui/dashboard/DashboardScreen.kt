package com.example.cleanarchitectureexample.ui.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {

    Box(contentAlignment = Alignment.Center) {
        Text(text = "Hi!")
    }

}

