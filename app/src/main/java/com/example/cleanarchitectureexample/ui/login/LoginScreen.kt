package com.example.cleanarchitectureexample.ui.login

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.ui.model.ButtonState
import com.example.cleanarchitectureexample.ui.theme.AppTheme
import com.example.cleanarchitectureexample.ui.utli.button.CustomButton
import com.example.cleanarchitectureexample.ui.utli.textfield.CustomTextField
import com.example.cleanarchitectureexample.ui.utli.textfield.CustomTextFieldType

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    val login by viewModel.loginState.collectAsState()
    val password by viewModel.passwordState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(AppTheme.colors.backgroundPrimary)
            .padding(
                start = 48.dp,
                end = 48.dp
            )
    ) {
        CustomTextField(
            state = login,
            hint = stringResource(id = R.string.hint_login),
            onValueChange = viewModel::loginChangeRequested,
            type = CustomTextFieldType.Text,
            singleLine = true
        )
        Spacer(modifier = Modifier.requiredHeight(12.dp))
        CustomTextField(
            state = password,
            hint = stringResource(id = R.string.hint_password),
            onValueChange = viewModel::passwordChangeRequested,
            type = CustomTextFieldType.Password,
            singleLine = true
        )
        Spacer(modifier = Modifier.requiredHeight(24.dp))
        CustomButton(
            text = stringResource(id = R.string.button_login),
            onClick = viewModel::loginButtonClicked
        )
    }
}

@Composable
fun TextButton(
    text: String,
    onClick: () -> Unit,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .clip(RoundedCornerShape(8.dp))
        .clickable { onClick.invoke() }
        .padding(
            start = 18.dp,
            end = 18.dp,
            top = 10.dp,
            bottom = 10.dp
        )
    ) {
        Text(
            text = text,
            color = textColor,
            letterSpacing = 1.sp
        )
    }
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