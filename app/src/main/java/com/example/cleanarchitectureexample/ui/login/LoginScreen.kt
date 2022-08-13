package com.example.cleanarchitectureexample.ui.login

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.ui.model.ButtonState
import com.example.cleanarchitectureexample.ui.model.FieldState

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    val login by viewModel.loginState.collectAsState()
    val password by viewModel.passwordState.collectAsState()
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
fun TextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .clip(RoundedCornerShape(8.dp))
        .clickable { onClick.invoke() }
        .padding(
            start = 16.dp,
            end = 16.dp,
            top = 8.dp,
            bottom = 8.dp
        )
    ) {
        Text(
            text = text,
            color = MaterialTheme.colors.primary
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
    state: ButtonState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = state == ButtonState.CLICKABLE,
        modifier = modifier.requiredHeight(48.dp)
    ) {
        Row(
            modifier = modifier.animateContentSize(
                animationSpec = tween()
            )
        ) {
            Text(
                text = stringResource(R.string.button_login),
            )
            if (state == ButtonState.IN_PROGRESS) {
                Spacer(modifier = Modifier.width(8.dp))
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.Gray,
                    strokeWidth = 2.dp
                )
            }
        }
    }
}