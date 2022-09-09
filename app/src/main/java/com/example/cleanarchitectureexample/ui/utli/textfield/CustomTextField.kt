package com.example.cleanarchitectureexample.ui.utli.textfield

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.cleanarchitectureexample.ui.model.FieldState
import com.example.cleanarchitectureexample.ui.theme.AppTheme
import com.example.cleanarchitectureexample.ui.utli.withAlpha

@Composable
fun CustomTextField(
    state: FieldState,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    singleLine: Boolean = false,
    error: String? = null,
    type: CustomTextFieldType
) {

    Column(
        modifier = modifier.animateContentSize()
    ) {

        val customTextSelectionColors = TextSelectionColors(
            handleColor = AppTheme.colors.primary,
            backgroundColor = AppTheme.colors.primary.withAlpha(alpha = 0.4f)
        )

        // changes selection color
        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            BasicTextField(
                enabled = state.isEnabled,
                cursorBrush = SolidColor(AppTheme.colors.primary),
                value = state.text,
                textStyle = AppTheme.styles.editTextTextStyle,
                onValueChange = onValueChange,
                singleLine = singleLine,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = when (type) {
                        CustomTextFieldType.Email -> false
                        CustomTextFieldType.Text -> true
                        CustomTextFieldType.Password -> false
                        CustomTextFieldType.Number -> false
                    },
                    keyboardType = when (type) {
                        CustomTextFieldType.Text -> KeyboardType.Text
                        CustomTextFieldType.Password -> KeyboardType.Password
                        CustomTextFieldType.Email -> KeyboardType.Email
                        CustomTextFieldType.Number -> KeyboardType.Number
                    }
                ),
                visualTransformation = when (type) {
                    CustomTextFieldType.Text -> VisualTransformation.None
                    CustomTextFieldType.Password -> PasswordVisualTransformation()
                    CustomTextFieldType.Email -> VisualTransformation.None
                    CustomTextFieldType.Number -> VisualTransformation.None
                },
                decorationBox = { innerTextField ->
                    Box(
                        Modifier.padding(16.dp)
                    ) {
                        if (state.text.isEmpty()) {
                            Text(
                                text = hint,
                                style = AppTheme.styles.editTextHintStyle
                            )
                        }
                        innerTextField()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = AppTheme.colors.backgroundSecondary,
                        shape = RoundedCornerShape(12.dp)
                    )
            )
        }
        if (!error.isNullOrBlank()) {
            Text(
                text = error,
                style = AppTheme.styles.editTextErrorStyle,
                modifier = Modifier.padding(4.dp)
            )
        }
    }

}

enum class CustomTextFieldType {
    Email,
    Text,
    Password,
    Number
}