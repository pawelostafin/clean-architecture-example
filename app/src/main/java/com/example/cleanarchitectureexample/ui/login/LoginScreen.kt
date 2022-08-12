package com.example.cleanarchitectureexample.ui.login

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cleanarchitectureexample.R

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    val login by viewModel.login.collectAsState()
    val password by viewModel.password.collectAsState()
    val loginButtonState by viewModel.loginButtonState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CustomTextField(
            state = login,
            hint = stringResource(id = R.string.hint_login),
            onValueChange = viewModel::loginChangeRequested
        )
        Spacer(modifier = Modifier.requiredHeight(8.dp))
        CustomTextField(
            state = password,
            hint = stringResource(id = R.string.hint_password),
            onValueChange = viewModel::passwordChangeRequested
        )
        Spacer(modifier = Modifier.requiredHeight(24.dp))
        LoginButton(
            state = loginButtonState,
            onClick = viewModel::loginButtonClicked
        )
    }

}

@Composable
fun CustomTextField(
    state: FieldState,
    onValueChange: (newValue: String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier
) {
    TextField(
        modifier = modifier,
        placeholder = { Text(text = hint) },
        value = state.text,
        enabled = state.isEnabled,
        onValueChange = onValueChange
    )
}

@Composable
fun LoginButton(
    state: LoginButtonState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = state == LoginButtonState.CLICKABLE,
        modifier = modifier.animateContentSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                LoginButtonState.IN_PROGRESS -> {
                    CircularProgressIndicator(
                        color = Color.White
                    )
                }
                LoginButtonState.CLICKABLE -> {
                    Text(
                        text = stringResource(R.string.button_login),
                    )
                }
            }
        }
    }
}

@Composable
fun ComposeProgressDialog() {
    val backgroundColor = remember { Color.Black.withAlpha(0.3f) }
    Box(
        modifier = Modifier
            .noRippleClickable {}
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

fun Color.withAlpha(alpha: Float): Color {
    return Color(
        red = red,
        green = green,
        blue = blue,
        alpha = alpha
    )
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick
    )
}