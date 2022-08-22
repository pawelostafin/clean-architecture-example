package com.example.cleanarchitectureexample.ui.dashboard

import android.view.ViewGroup
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import coil.compose.AsyncImage
import com.example.cleanarchitectureexample.ui.theme.AppTheme
import com.example.cleanarchitectureexample.ui.utli.clickableWithRipple
import com.example.cleanarchitectureexample.ui.utli.withAlpha

object Diff : DiffUtil.ItemCallback<MarketItem>() {

    override fun areItemsTheSame(oldItem: MarketItem, newItem: MarketItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MarketItem, newItem: MarketItem): Boolean {
        return oldItem == newItem
    }

}

class EloAdapter(
    private val onItemClick: (MarketItem) -> Unit
) : ListAdapter<MarketItem, RecyclerView.ViewHolder>(Diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MarketViewHolder(
            composeView = ComposeView(parent.context),
            onItemClick = onItemClick
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MarketViewHolder).bind(getItem(position))
    }

}

class MarketViewHolder(
    private val composeView: ComposeView,
    private val onItemClick: (MarketItem) -> Unit
) : RecyclerView.ViewHolder(composeView) {

//    private var itemState by mutableStateOf<MarketItem?>(null)

    fun bind(item: MarketItem) {
        composeView.setContent {
            MarketCell(
                item = item,
                onClick = onItemClick
            )
        }
    }
}

@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val profileButtonState by viewModel.profileButtonState.collectAsState()
    val items by viewModel.items.collectAsState()
    val progressViewVisibility by viewModel.progressViewVisibility.collectAsState()

    val profileButtonId = remember { "profileButton" }
    val marketListId = remember { "marketList" }
    val progressViewId = remember { "progressView" }
    val constraints = remember {
        createConstraints(
            profileButtonId = profileButtonId,
            marketListId = marketListId,
            progressViewId = progressViewId,
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

//        AndroidView(
//            modifier = Modifier
//                .layoutId(marketListId),
//            factory = {
//                RecyclerView(it).apply {
//                    layoutManager = LinearLayoutManager(it)
//                    adapter = EloAdapter(onItemClick = viewModel::itemClicked)
//                    (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
//                    setHasFixedSize(true)
//                }
//            }
//        ) {
//            (it.adapter as EloAdapter).submitList(items)
//        }
        LazyColumn(
            modifier = Modifier
                .layoutId(marketListId)
        ) {
            items(
                items = items ?: emptyList(),
                key = { it.id }
            ) {
                MarketCell(
                    item = it,
                    onClick = viewModel::itemClicked
                )
            }
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
                    text = item.name,
                    color = AppTheme.colors.textColorPrimary,
                    fontSize = 18.sp
                )
                Text(
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
    AsyncImage(
        modifier = modifier
            .clip(CircleShape)
            .background(AppTheme.colors.textColorPrimary),
        model = url,
        contentDescription = null
    )
}

private fun createConstraints(
    profileButtonId: String,
    marketListId: String,
    progressViewId: String
): ConstraintSet {
    return ConstraintSet {
        val profileButton = createRefFor(profileButtonId)
        val marketList = createRefFor(marketListId)
        val progressView = createRefFor(progressViewId)

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
