package com.example.cleanarchitectureexample.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage

@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {

    val profileButtonState by viewModel.profileButtonState.collectAsState()

    ConstraintLayout {
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
            .shadow(
                elevation = 12.dp,
                shape = shape
            )
            .requiredSize(64.dp)
            .clip(shape)
            .background(color = Color.LightGray)
            .clickable { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is ProfileButtonState.Data -> {
                Text(
                    text = state.firstLetterOfFirstName,
                    fontSize = 26.sp,
                    color = Color.Gray,
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
                    color = Color.Gray,
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 3.dp
                )
            }
        }
    }
}
