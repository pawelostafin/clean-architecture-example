package com.example.cleanarchitectureexample.ui.utli.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.ui.login.TextButton
import com.example.cleanarchitectureexample.ui.theme.AppTheme

@Composable
fun ErrorDialog(
    message: String,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        title = { Text(text = stringResource(R.string.error_dialog_title)) },
        text = { Text(text = message) },
        shape = RoundedCornerShape(12.dp),
        contentColor = AppTheme.colors.textColorPrimary,
        onDismissRequest = onDismissRequest,
        backgroundColor = AppTheme.colors.backgroundSecondary,
        buttons = {
            Column {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextButton(
                        text = stringResource(R.string.dialog_button_ok),
                        onClick = onDismissRequest
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    )
}