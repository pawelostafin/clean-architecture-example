package com.example.cleanarchitectureexample.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.cleanarchitectureexample.ui.theme.AppTheme
import com.example.cleanarchitectureexample.ui.utli.clickableWithRipple
import com.example.cleanarchitectureexample.ui.utli.withAlpha

@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val profileButtonState by viewModel.profileButtonState.collectAsState()

    ConstraintLayout(
        modifier = Modifier
            .background(color = AppTheme.colors.backgroundPrimary)
    ) {
        val (profileButton) = createRefs()

        ProfileButton(
            state = profileButtonState,
            onClick = viewModel::profileButtonClicked,
            modifier = Modifier
                .constrainAs(profileButton) {
                    top.linkTo(anchor = parent.top, margin = 16.dp)
                    end.linkTo(anchor = parent.end, margin = 16.dp)
                }
        )
    }

}

@Composable
fun ProfileButton(
    state: ProfileButtonState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = remember { CircleShape }
    Box(
        modifier = modifier
            .requiredSize(64.dp)
            .clip(shape)
            .background(AppTheme.colors.primary.withAlpha(0.2f))
            .padding(2.dp)
            .clip(shape)
            .background(AppTheme.colors.backgroundSecondary)
            .clickableWithRipple { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is ProfileButtonState.Data -> {
                Text(
                    text = state.firstLetterOfFirstName,
                    fontSize = 26.sp,
                    color = AppTheme.colors.textColorSecondary,
                    fontWeight = FontWeight.Medium
                )
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    model = state.imageUrl,
                    contentDescription = null
                )
            }
            ProfileButtonState.InProgress -> {
                CircularProgressIndicator(
                    color = AppTheme.colors.textColorSecondary,
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 3.dp
                )
            }
        }
    }
}
