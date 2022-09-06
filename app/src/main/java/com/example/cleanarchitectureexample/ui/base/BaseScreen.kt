package com.example.cleanarchitectureexample.ui.base

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.ui.utli.dialog.ErrorDialog
import com.example.cleanarchitectureexample.ui.utli.dialog.FullscreenProgressDialog

@Composable
fun <N, VM : ComposeBaseViewModel<N>> BaseScreen(
    viewModel: VM,
    navigate: (N) -> Unit,
    content: @Composable () -> Unit,
) {
    val progressDialogVisibility by viewModel.progressDialogVisibility.collectAsState()
    val errorDialogState by viewModel.errorDialogState.collectAsState()

    content.invoke()

    ProgressDialogHandler(isVisible = progressDialogVisibility)
    ErrorDialogHandler(
        state = errorDialogState,
        onDismissRequest = viewModel::errorDialogDismissRequested
    )

    LaunchedEffect(key1 = Unit) {
        observeFlow(
            flowProvider = { viewModel.navigation },
            scope = this,
            onEach = navigate
        )
    }

    BackHandler {
        viewModel.systemBackButtonClicked()
    }
}

@Composable
private fun ProgressDialogHandler(isVisible: Boolean) {
    if (isVisible) {
        FullscreenProgressDialog()
    }
}

@Composable
private fun ErrorDialogHandler(
    state: ErrorDialogState,
    onDismissRequest: () -> Unit
) {
    when (state) {
        ErrorDialogState.Hidden -> Unit
        is ErrorDialogState.Visible -> {
            ErrorDialog(
                message = state.throwable.toErrorMessage(),
                onDismissRequest = onDismissRequest
            )
        }
    }
}

@Composable
private fun Throwable.toErrorMessage(): String {
    val default = stringResource(R.string.error_message_unexpected)
    return message ?: default
}

sealed class ErrorDialogState {
    object Hidden : ErrorDialogState()
    data class Visible(val throwable: Throwable) : ErrorDialogState()
}
