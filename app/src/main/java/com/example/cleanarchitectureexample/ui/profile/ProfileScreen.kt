package com.example.cleanarchitectureexample.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.cleanarchitectureexample.ui.utli.clickableWithRipple

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val profileInfo by viewModel.profileInfo.collectAsState()
    ConstraintLayout {
        val (profileImage, buttonsLayout, closeButton, fullNameTextView) = createRefs()

        ProfileImage(
            modifier = Modifier
                .requiredSize(160.dp)
                .constrainAs(profileImage) {
                    top.linkTo(parent.top, 100.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            imageUrl = profileInfo?.imageUrl
        )

        Text(
            modifier = Modifier
                .constrainAs(fullNameTextView) {
                    top.linkTo(profileImage.bottom, 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = profileInfo?.fullName ?: "",
            fontSize = 26.sp
        )

        Column(
            modifier = Modifier
                .constrainAs(buttonsLayout) {
                    top.linkTo(fullNameTextView.bottom, 12.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }
        ) {
            ProfileButton(
                text = "SETTINGS",
                modifier = Modifier
                    .height(48.dp),
//                onClick = viewModel::logoutButtonClicked
                onClick = {}
            )
            ProfileDivider()
            ProfileButton(
                text = "LOGOUT",
                modifier = Modifier
                    .height(48.dp),
                onClick = viewModel::logoutButtonClicked
            )
        }


        CloseButton(
            modifier = Modifier
                .requiredSize(56.dp)
                .constrainAs(closeButton) {
                    top.linkTo(parent.top, 8.dp)
                    end.linkTo(parent.end, 8.dp)
                },
            onClick = viewModel::closeButtonClicked
        )
    }
}

@Composable
fun CloseButton(
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
            painter = rememberVectorPainter(image = Icons.Filled.Close),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Gray)
        )
    }
}

@Composable
fun ProfileDivider(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp)
    ) {
        Divider(
            color = Color.LightGray
        )
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

@Composable
fun ProfileButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickableWithRipple { onClick.invoke() }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = text,
                color = Color.Gray,
                modifier = Modifier,
                letterSpacing = 1.sp
            )
        }
    }

}