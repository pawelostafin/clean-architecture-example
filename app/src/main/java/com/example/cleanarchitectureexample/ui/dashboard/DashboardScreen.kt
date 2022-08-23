package com.example.cleanarchitectureexample.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.cleanarchitectureexample.ui.settings.CustomDropdownMenu
import com.example.cleanarchitectureexample.ui.theme.AppTheme
import com.example.cleanarchitectureexample.ui.utli.clickableWithRipple
import com.example.cleanarchitectureexample.ui.utli.withAlpha
import com.example.domain.model.CurrencyCode

@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val profileButtonState by viewModel.profileButtonState.collectAsState()
    val items by viewModel.items.collectAsState()
    val progressViewVisibility by viewModel.progressViewVisibility.collectAsState()
    val baseCurrencySelectorState by viewModel.baseCurrencySelectorState.collectAsState()

    val profileButtonId = remember { "profileButton" }
    val marketListId = remember { "marketList" }
    val progressViewId = remember { "progressView" }
    val baseCurrencySelectorId = remember { "baseCurrencySelector" }
    val constraints = remember {
        createConstraints(
            profileButtonId = profileButtonId,
            marketListId = marketListId,
            progressViewId = progressViewId,
            baseCurrencySelectorId = baseCurrencySelectorId
        )
    }

    ConstraintLayout(
        constraintSet = constraints,
        modifier = Modifier
            .background(color = AppTheme.colors.backgroundPrimary)
    ) {
        if (progressViewVisibility) {
            CircularProgressIndicator(
                modifier = Modifier.layoutId(progressViewId),
                color = AppTheme.colors.primary
            )
        }

        LazyColumn(
            modifier = Modifier
                .layoutId(marketListId)
        ) {
            item {
                Spacer(modifier = Modifier.height(64.dp))
            }
            items(
                items = items ?: emptyList(),
                key = { it.id }
            ) {
                MarketCell(
                    item = it,
                    onClick = viewModel::itemClicked
                )
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        baseCurrencySelectorState?.let {
            BaseCurrencySelector(
                modifier = Modifier.layoutId(baseCurrencySelectorId),
                state = it,
                onDismissRequest = viewModel::baseCurrencySelectorDismissRequested,
                onBaseCurrencyChangeRequest = viewModel::baseCurrencyChangeRequested,
                onClick = viewModel::baseCurrencySelectorClicked
            )
        }

        ProfileButton(
            state = profileButtonState,
            onClick = viewModel::profileButtonClicked,
            modifier = Modifier
                .layoutId(profileButtonId)
        )
    }

}

@Composable
private fun BaseCurrencySelector(
    modifier: Modifier = Modifier,
    state: BaseCurrencySelectorState,
    onDismissRequest: () -> Unit,
    onBaseCurrencyChangeRequest: (CurrencyCode) -> Unit,
    onClick: () -> Unit
) {
    Box(modifier = modifier.clickableWithRipple { onClick.invoke() }) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = "Base currency: ${state.baseCurrency.toStringValue()}",
            color = AppTheme.colors.textColorPrimary,
            fontSize = 18.sp
        )
        CustomDropdownMenu(
            isDropdownVisible = state.isDropdownVisible,
            onDismissRequest = onDismissRequest
        ) {
            state.baseCurrencyOptions.forEach {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickableWithRipple { onBaseCurrencyChangeRequest.invoke(it) }
                        .padding(
                            start = 18.dp,
                            end = 18.dp,
                            top = 12.dp,
                            bottom = 12.dp
                        ),
                    text = it.toStringValue(),
                    fontSize = 18.sp,
                    color = AppTheme.colors.textColorPrimary
                )
            }
        }
    }
}

fun CurrencyCode.toStringValue(): String {
    return rawValue.uppercase()
}

@Composable
private fun MarketCell(
    item: MarketItem,
    onClick: (MarketItem) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .clickableWithRipple { onClick.invoke(item) }
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 8.dp,
                    bottom = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CurrencyImage(
                modifier = Modifier.requiredSize(32.dp),
                url = item.imageUrl
            )
            Spacer(modifier = Modifier.width(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = item.name,
                    color = AppTheme.colors.textColorPrimary,
                    fontSize = 18.sp
                )
                Text(
                    modifier = Modifier.weight(2f),
                    text = item.price.toPlainString(),
                    color = AppTheme.colors.textColorSecondary,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun CurrencyImage(
    url: String?,
    modifier: Modifier = Modifier
) {
    val circleShape = remember { CircleShape }
    AsyncImage(
        modifier = modifier
            .clip(circleShape)
            .background(Color.White)
            .padding(1.dp)
            .clip(circleShape),
        model = url,
        contentDescription = null
    )
}

private fun createConstraints(
    profileButtonId: String,
    marketListId: String,
    progressViewId: String,
    baseCurrencySelectorId: String,
): ConstraintSet {
    return ConstraintSet {
        val profileButton = createRefFor(profileButtonId)
        val marketList = createRefFor(marketListId)
        val progressView = createRefFor(progressViewId)
        val baseCurrencySelector = createRefFor(baseCurrencySelectorId)

        constrain(profileButton) {
            top.linkTo(parent.top, 8.dp)
            end.linkTo(parent.end, 8.dp)
        }
        constrain(marketList) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)

            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }
        constrain(progressView) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(baseCurrencySelector) {
            top.linkTo(parent.top, 8.dp)
            start.linkTo(parent.start, 8.dp)
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
            .requiredSize(56.dp)
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
