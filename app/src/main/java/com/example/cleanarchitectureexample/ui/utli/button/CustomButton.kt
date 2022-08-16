package com.example.cleanarchitectureexample.ui.utli.button

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cleanarchitectureexample.ui.theme.AppTheme

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    val scale = remember { mutableStateOf(1f) }
    val animatedScale = animateFloatAsState(
        animationSpec = spring(),
        targetValue = scale.value
    )
    val backgroundShape = remember { RoundedCornerShape(12.dp) }
    val alpha = if (enabled) 1f else 0.2f

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .scale(animatedScale.value)
            .clip(backgroundShape)
            .alpha(alpha)
            .background(color = AppTheme.colors.primary)
            .gestureRecognizer(
                enabled = enabled,
                onClick = { onClick.invoke() },
                onPressStart = { scale.value = 0.93f },
                onPressEnd = { scale.value = 1f },
            )
    ) {
        Text(
            text = text.uppercase(),
            letterSpacing = 0.5.sp,
            style = TextStyle(
                color = AppTheme.colors.backgroundPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.W500
            ),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}

fun Modifier.gestureRecognizer(
    onClick: (Offset) -> Unit = {},
    onPressStart: () -> Unit = {},
    onPressEnd: () -> Unit = {},
    enabled: Boolean = true
): Modifier {
    return if (enabled) {
        pointerInput(Unit) {
            detectTapGestures(
                onTap = {
                    onClick(it)
                },
                onPress = {
                    onPressStart()
                    runCatching {
                        tryAwaitRelease()
                    }
                    onPressEnd()
                },
            )
        }
    } else {
        this
    }
}