package com.example.cleanarchitectureexample.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val imageUrl by viewModel.profileImageUrl.collectAsState()
    Column {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (profileImage, logoutButton) = createRefs()

            ProfileImage(
                modifier = Modifier
                    .requiredSize(160.dp)
                    .constrainAs(profileImage) {
                        top.linkTo(parent.top, 100.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                imageUrl = imageUrl
            )
            Button(
                modifier = Modifier
                    .constrainAs(logoutButton) {
                        top.linkTo(profileImage.bottom, 50.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                onClick = viewModel::logoutButtonClicked
            ) {
                Text(text = "LOGOUT")
            }
        }
    }
}

@Composable
fun ProfileImage(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}