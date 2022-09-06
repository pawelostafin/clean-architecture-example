package com.example.cleanarchitectureexample.ui.details

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.cleanarchitectureexample.ui.base.BaseScreen
import com.example.cleanarchitectureexample.ui.dashboard.CurrencyImage
import com.example.cleanarchitectureexample.ui.dashboard.toStringValue
import com.example.cleanarchitectureexample.ui.settings.BackButton
import com.example.cleanarchitectureexample.ui.theme.AppTheme
import com.example.cleanarchitectureexample.ui.utli.withAlpha
import com.example.domain.model.ChartData
import com.example.domain.model.ChartDataTrend
import com.example.domain.model.ChartPoint
import java.math.BigDecimal

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    navigate: (DetailsViewModel.Navigation) -> Unit
) = BaseScreen(
    viewModel = viewModel,
    navigate = navigate
) {
    val marketInfo by viewModel.marketInfo.collectAsState()
    val chartData by viewModel.chartData.collectAsState()

    Box(
        modifier = Modifier
            .background(AppTheme.colors.backgroundPrimary),
    ) {
        ScrollableContent(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            chartData = chartData,
            marketInfo = marketInfo
        )
        BackButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 8.dp, top = 8.dp),
            onClick = { viewModel.backButtonClicked() }
        )
    }
}

@Composable
fun ScrollableContent(
    modifier: Modifier = Modifier,
    chartData: ChartData?,
    marketInfo: MarketInfo?
) {
    val chartId = remember { "chartId" }
    val detailsContainerId = remember { "detailsContainerId" }

    val constraints = remember {
        createConstraints(
            chartId = chartId,
            detailsContainerId = detailsContainerId
        )
    }
    ConstraintLayout(
        modifier = modifier,
        constraintSet = constraints,
    ) {

        chartData?.let {
            Chart(
                modifier = Modifier.layoutId(chartId),
                chartData = it
            )
        }
        marketInfo?.let {
            MarketInfoView(
                modifier = Modifier.layoutId(detailsContainerId),
                marketInfo = it
            )
        }
    }
}

@Composable
private fun MarketInfoView(
    modifier: Modifier = Modifier,
    marketInfo: MarketInfo
) {
    val topSpacerHeight = remember { 12.dp }
    val spacerHeight = remember { 2.dp }
    val bottomSpacerHeight = remember { 24.dp }
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CurrencyImage(
                url = marketInfo.imageUrl,
                modifier = Modifier.requiredSize(40.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = marketInfo.name,
                fontSize = 32.sp,
                color = AppTheme.colors.textColorPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        val currentPriceText = remember(marketInfo) {
            marketInfo.currentPrice.toPlainString() + " " + marketInfo.baseCurrency.toStringValue()
        }
        Text(
            text = currentPriceText,
            fontSize = 24.sp,
            color = AppTheme.colors.textColorPrimary
        )
        Spacer(modifier = Modifier.height(topSpacerHeight))
        MarketInfoRow(
            title = "Change 24h:",
            value = marketInfo.priceChange24h.toPlainString(),
            valueColor = marketInfo.priceChange24h.toTrendColor()
        )
        Spacer(modifier = Modifier.height(spacerHeight))
        MarketInfoRow(
            title = "Change 24h %:",
            value = marketInfo.priceChangePercentage24h.toPlainString() + "%",
            valueColor = marketInfo.priceChangePercentage24h.toTrendColor()
        )
        Spacer(modifier = Modifier.height(spacerHeight))
        MarketInfoRow(
            title = "High 24h:",
            value = marketInfo.high24h.toPlainString()
        )
        Spacer(modifier = Modifier.height(spacerHeight))
        MarketInfoRow(
            title = "Low 24h:",
            value = marketInfo.low24h.toPlainString()
        )
        Spacer(modifier = Modifier.height(spacerHeight))
        MarketInfoRow(
            title = "Total Volume:",
            value = marketInfo.totalVolume.toPlainString()
        )
        Spacer(modifier = Modifier.height(spacerHeight))
        MarketInfoRow(
            title = "Market Cap rank:",
            value = marketInfo.marketCapRank.toString(),
        )
        Spacer(modifier = Modifier.height(spacerHeight))
        MarketInfoRow(
            title = "Market Cap:",
            value = marketInfo.marketCap.toString(),
        )
        Spacer(modifier = Modifier.height(bottomSpacerHeight))
    }
}

@Composable
private fun MarketInfoRow(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    titleColor: Color = AppTheme.colors.textColorSecondary,
    valueColor: Color = AppTheme.colors.textColorPrimary
) {
    val weight1 = remember { 1.5f }
    val weight2 = remember { 3f }
    val textSize = remember { 16.sp }
    val spacerWidth = remember { 4.dp }
    Row(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.weight(weight1),
            text = title,
            fontSize = textSize,
            color = titleColor
        )
        Spacer(modifier = Modifier.width(spacerWidth))
        Text(
            modifier = Modifier.weight(weight2),
            text = value,
            fontSize = textSize,
            color = valueColor
        )
    }
}

@Composable
private fun BigDecimal.toTrendColor(): Color {
    return when {
        this > BigDecimal.ZERO -> AppTheme.colors.chartGreen
        this < BigDecimal.ZERO -> AppTheme.colors.chartRed
        else -> AppTheme.colors.chartNeutral
    }
}

private fun createConstraints(
    chartId: String,
    detailsContainerId: String
): ConstraintSet {
    return ConstraintSet {
        val chart = createRefFor(chartId)
        val detailsContainer = createRefFor(detailsContainerId)

        constrain(chart) {
            top.linkTo(parent.top, 56.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)

            height = Dimension.fillToConstraints
            width = Dimension.ratio("3:1")
        }

        constrain(detailsContainer) {
            top.linkTo(chart.bottom, 12.dp)
            start.linkTo(parent.start, 24.dp)
        }
    }
}

@Composable
fun Chart(
    chartData: ChartData,
    modifier: Modifier = Modifier
) {
    val lineWidth = 1.dp.pxValue
    val chartColor = when (chartData.trend) {
        ChartDataTrend.UP -> AppTheme.colors.chartGreen
        ChartDataTrend.DOWN -> AppTheme.colors.chartRed
        ChartDataTrend.EQUAL -> AppTheme.colors.chartNeutral
    }
    val backgroundColor = remember(chartColor) { chartColor.withAlpha(0.3f) }
    val animatedLinePercentage = remember { Animatable(0f) }
    val backgroundAlpha = remember { Animatable(0f) }
    val drawBackground = remember { mutableStateOf(false) }
    val animationEasing = remember { CubicBezierEasing(0.5f, 0f, 0.5f, 1f) }

    LaunchedEffect(animatedLinePercentage) {
        animatedLinePercentage.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                easing = animationEasing,
                durationMillis = 1000,
                delayMillis = 400
            ),
        )
        drawBackground.value = true
        backgroundAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                easing = animationEasing,
                durationMillis = 1000,
            )
        )
    }

    Canvas(modifier = modifier) {
        val linePath = createLinePath(
            chartData = chartData,
            canvasSize = size
        )
        val backgroundPath = createBackgroundPath(
            linePath = linePath,
            canvasSize = size
        )
        val animatedLinePath = getLinePathPercent(
            originalPath = linePath,
            percent = animatedLinePercentage.value
        )

        if (drawBackground.value) {
            drawPath(
                path = backgroundPath,
                brush = Brush.verticalGradient(
                    colors = listOf(backgroundColor, Color.Transparent)
                ),
                alpha = backgroundAlpha.value
            )
        }

        drawPath(
            path = animatedLinePath,
            color = chartColor,
            style = Stroke(
                width = lineWidth,
                cap = StrokeCap.Round
            )
        )
    }
}

private fun createBackgroundPath(
    linePath: Path,
    canvasSize: Size
): Path {
    val outputPath = Path()
    PathMeasure().let {
        it.setPath(linePath, false)
        it.getSegment(
            startDistance = 0f,
            stopDistance = it.length,
            destination = outputPath
        )
    }
    return outputPath.apply {
        lineTo(x = canvasSize.width, y = canvasSize.height)
        lineTo(x = 0f, y = canvasSize.height)
        close()
    }
}

private fun createLinePath(
    chartData: ChartData,
    canvasSize: Size
): Path {
    val offsets = chartData.points.map {
        it.normalize(
            canvasSize = canvasSize,
            maxXCount = chartData.points.size,
            maxYValue = chartData.maxY,
            minYValue = chartData.minY,
        )
    }
    return Path().apply {
        val easing = 0.5f
        val initial = offsets.first()
        moveTo(initial.x, initial.y)
        offsets.forEachIndexed { index, value ->
            if (index > 0) {
                val previous = offsets.getOrNull(index - 1) ?: initial

                val deltaX = value.x - previous.x
                val curveXOffset = deltaX * easing

                cubicTo(
                    x1 = previous.x + curveXOffset,
                    y1 = previous.y,
                    x2 = value.x - curveXOffset,
                    y2 = value.y,
                    x3 = value.x,
                    y3 = value.y
                )
            }
        }
    }
}

private fun getLinePathPercent(
    originalPath: Path,
    percent: Float
): Path {
    val outputPath = Path()
    PathMeasure().let {
        it.setPath(originalPath, false)
        it.getSegment(
            startDistance = 0f,
            stopDistance = it.length * percent,
            destination = outputPath
        )
    }
    return outputPath
}

private fun ChartPoint.normalize(
    canvasSize: Size,
    maxXCount: Int,
    maxYValue: Float,
    minYValue: Float
): Offset {
    val normalizedX = x * (canvasSize.width / (maxXCount - 1).toFloat())

    val heightOfOneUnit = (canvasSize.height / (maxYValue - minYValue))
    val normalizedY = canvasSize.height - ((y - minYValue) * heightOfOneUnit)

    return Offset(
        x = normalizedX,
        y = normalizedY
    )
}

val Dp.pxValue: Float
    @Composable
    get() = with(LocalDensity.current) { toPx() }