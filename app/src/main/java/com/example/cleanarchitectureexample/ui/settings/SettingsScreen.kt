package com.example.cleanarchitectureexample.ui.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.ui.base.BaseScreen
import com.example.cleanarchitectureexample.ui.theme.AppTheme
import com.example.cleanarchitectureexample.ui.utli.clickableWithRipple
import com.example.domain.model.DarkThemeMode

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navigate: (SettingsViewModel.Navigation) -> Unit
) = BaseScreen(
    viewModel = viewModel,
    navigate = navigate
) {
    val darkThemeMode by viewModel.darkThemeMode.collectAsState()
    val darkThemeModeDropdownVisibility by viewModel.darkThemeModeDropdownVisibility.collectAsState()

    val backButtonId = remember { "backButtonId" }
    val settingsColumnId = remember { "settingsColumnId" }
    val constraints = remember {
        createConstraints(
            backButtonId = backButtonId,
            settingsColumnId = settingsColumnId
        )
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.backgroundPrimary),
        constraintSet = constraints
    ) {
        BackButton(
            modifier = Modifier.layoutId(backButtonId),
            onClick = { viewModel.backButtonClicked() }
        )

        Column(
            modifier = Modifier.layoutId(settingsColumnId),
        ) {
            DarkModeSettingsItem(
                mode = darkThemeMode,
                onClick = viewModel::darkThemeItemClicked,
                isDropdownVisible = darkThemeModeDropdownVisibility,
                onDismissRequest = viewModel::darkThemeDropdownDismissRequested,
                onModeChangeRequest = viewModel::darkThemeModeChangeRequested
            )
        }
    }
}

private fun createConstraints(
    backButtonId: String,
    settingsColumnId: String
): ConstraintSet {
    return ConstraintSet {
        val backButton = createRefFor(backButtonId)
        val settingsColumn = createRefFor(settingsColumnId)

        constrain(settingsColumn) {
            top.linkTo(backButton.bottom, 24.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }
        constrain(backButton) {
            top.linkTo(parent.top, 8.dp)
            start.linkTo(parent.start, 8.dp)
        }
    }
}

@Composable
fun DarkModeSettingsItem(
    mode: DarkThemeMode,
    isDropdownVisible: Boolean,
    onDismissRequest: () -> Unit,
    onModeChangeRequest: (DarkThemeMode) -> Unit,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .clickableWithRipple { onClick.invoke() }
            .fillMaxWidth()
            .padding(start = 24.dp, top = 12.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Dark theme",
            color = AppTheme.colors.textColorPrimary,
            fontSize = 18.sp
        )
        Box {
            Text(
                modifier = Modifier.padding(
                    top = 8.dp,
                    bottom = 8.dp,
                    end = 24.dp
                ),
                text = mode.toStringResource(),
                color = AppTheme.colors.textColorSecondary,
                fontSize = 18.sp
            )
            CustomDropdownMenu(
                isDropdownVisible = isDropdownVisible,
                onDismissRequest = onDismissRequest,
                offset = DpOffset(x = 8.dp, y = 0.dp)
            ) {
                DarkThemeMode.values().forEach {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickableWithRipple { onModeChangeRequest.invoke(it) }
                            .padding(
                                start = 18.dp,
                                end = 18.dp,
                                top = 12.dp,
                                bottom = 12.dp
                            ),
                        fontSize = 18.sp,
                        text = it.toStringResource(),
                        color = AppTheme.colors.textColorPrimary
                    )
                }
            }
        }
    }
}

@Composable
fun CustomDropdownMenu(
    modifier: Modifier = Modifier,
    isDropdownVisible: Boolean,
    onDismissRequest: () -> Unit,
    offset: DpOffset = DpOffset.Zero,
    content: @Composable ColumnScope.() -> Unit
) {
    MaterialTheme(
        shapes = MaterialTheme.shapes.copy(
            medium = RoundedCornerShape(12.dp)
        ),
        colors = MaterialTheme.colors.copy(
            surface = AppTheme.colors.dropdownBackground
        )
    ) {
        DropdownMenu(
            modifier = modifier,
            expanded = isDropdownVisible,
            onDismissRequest = onDismissRequest,
            offset = offset,
            content = content
        )
    }
}

@Composable
private fun DarkThemeMode.toStringResource(): String {
    return when (this) {
        DarkThemeMode.ENABLED -> stringResource(R.string.settings_dark_mode_enabled)
        DarkThemeMode.DISABLED -> stringResource(R.string.settings_dark_mode_disabled)
        DarkThemeMode.AUTO -> stringResource(R.string.settings_dark_mode_auto)
    }
}

@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .clickableWithRipple { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.requiredSize(32.dp),
            painter = rememberVectorPainter(image = Icons.Filled.ArrowBack),
            contentDescription = null,
            colorFilter = ColorFilter.tint(AppTheme.colors.textColorSecondary)
        )
    }
}