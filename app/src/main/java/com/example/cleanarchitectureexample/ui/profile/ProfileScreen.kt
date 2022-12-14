package com.example.cleanarchitectureexample.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.ui.base.BaseScreen
import com.example.cleanarchitectureexample.ui.theme.AppTheme
import com.example.cleanarchitectureexample.ui.utli.clickableWithRipple
import com.example.cleanarchitectureexample.ui.utli.withAlpha

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    navigate: (ProfileViewModel.Navigation) -> Unit
) = BaseScreen(
    viewModel = viewModel,
    navigate = navigate
) {
    val profileInfo by viewModel.profileInfo.collectAsState()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.backgroundPrimary)
    ) {
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
            fontSize = 26.sp,
            color = AppTheme.colors.textColorPrimary
        )

        Column(
            modifier = Modifier
                .constrainAs(buttonsLayout) {
                    top.linkTo(fullNameTextView.bottom, 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }
        ) {
            ProfileListButton(
                text = stringResource(R.string.profile_button_settings),
                modifier = Modifier
                    .height(48.dp),
                onClick = viewModel::settingsButtonClicked
            )
            ProfileDivider()
            ProfileListButton(
                text = stringResource(id = R.string.profile_button_logout),
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
            colorFilter = ColorFilter.tint(AppTheme.colors.textColorSecondary)
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
            color = AppTheme.colors.backgroundSecondary
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
            .background(AppTheme.colors.profileImageBorder.withAlpha(0.2f))
            .padding(3.dp)
            .clip(CircleShape)
            .background(AppTheme.colors.backgroundSecondary),
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
fun ProfileListButton(
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
                letterSpacing = 2.sp,
                fontWeight = FontWeight(450),
                fontSize = 16.sp
            )
        }
    }

}

@Composable
fun CustomModalDrawer(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onOpenRequest: () -> Unit,
    isExpanded: Boolean,
    drawerContent: @Composable (ColumnScope) -> Unit,
    content: @Composable () -> Unit
) {

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed,
        confirmStateChange = {
            when (it) {
                DrawerValue.Closed -> onDismissRequest.invoke()
                DrawerValue.Open -> onOpenRequest.invoke()
            }
            false
        }
    )

    LaunchedEffect(key1 = isExpanded) {
        if (isExpanded) {
            drawerState.open()
        } else {
            drawerState.close()
        }
    }

    ModalDrawer(
        drawerContent = drawerContent,
        drawerState = drawerState,
        content = content
    )
}