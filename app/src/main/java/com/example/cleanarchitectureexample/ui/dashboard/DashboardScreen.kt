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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil.compose.AsyncImage
import com.example.cleanarchitectureexample.ui.theme.AppTheme
import com.example.cleanarchitectureexample.ui.utli.button.CustomButton
import com.example.cleanarchitectureexample.ui.utli.clickableWithRipple
import com.example.cleanarchitectureexample.ui.utli.withAlpha

@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val profileButtonState by viewModel.profileButtonState.collectAsState()

    val profileButtonId = remember { "profileButton" }
    val detailsButtonId = remember { "detailsButton" }
    val constraints = remember {
        createConstraints(
            profileButtonId = profileButtonId,
            detailsButtonId = detailsButtonId
        )
    }

    ConstraintLayout(
        constraintSet = constraints,
        modifier = Modifier
            .background(color = AppTheme.colors.backgroundPrimary)
    ) {
        ProfileButton(
            state = profileButtonState,
            onClick = viewModel::profileButtonClicked,
            modifier = Modifier
                .layoutId(profileButtonId)
        )
        CustomButton(
            text = "Go to details",
            onClick = viewModel::goToDetailsButtonClicked,
            modifier = Modifier
                .layoutId(detailsButtonId)
        )
    }

}

private fun createConstraints(
    profileButtonId: String,
    detailsButtonId: String
): ConstraintSet {
    return ConstraintSet {
        val profileButton = createRefFor(profileButtonId)
        val detailsButton = createRefFor(detailsButtonId)

        constrain(profileButton) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }
        constrain(detailsButton) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
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
            .background(AppTheme.colors.profileImageBorder.withAlpha(0.2f))
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
