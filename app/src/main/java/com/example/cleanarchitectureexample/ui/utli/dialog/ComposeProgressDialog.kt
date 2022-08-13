package com.example.cleanarchitectureexample.ui.utli.dialog

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog

@Composable
fun ComposeProgressDialog() {
    Dialog(
        onDismissRequest = {}
    ) {
        CircularProgressIndicator(
            color = Color.White
        )
    }
}