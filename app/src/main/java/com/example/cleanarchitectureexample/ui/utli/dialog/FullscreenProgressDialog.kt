package com.example.cleanarchitectureexample.ui.utli.dialog

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import com.example.cleanarchitectureexample.ui.theme.AppTheme

@Composable
fun FullscreenProgressDialog() {
    Dialog(
        onDismissRequest = {}
    ) {
        CircularProgressIndicator(
            color = AppTheme.colors.primary
        )
    }
}