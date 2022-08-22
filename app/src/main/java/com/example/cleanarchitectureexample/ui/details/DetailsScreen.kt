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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.cleanarchitectureexample.ui.settings.BackButton
import com.example.cleanarchitectureexample.ui.theme.AppTheme
import com.example.cleanarchitectureexample.ui.utli.withAlpha
import com.example.domain.model.ChartData
import com.example.domain.model.ChartPoint
import java.math.BigDecimal

@Composable
fun DetailsScreen(viewModel: DetailsViewModel) {
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
                .align(Alignment.TopStart),
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
            Column(
                modifier = Modifier.layoutId(detailsContainerId)
            ) {
                val weight1 = remember { 1.5f }
                val weight2 = remember { 3f }

                Text(
                    text = it.name,
                    fontSize = 32.sp,
                    color = AppTheme.colors.textColorPrimary
                )
                Text(
                    text = it.currentPrice.toPlainString() + " " + it.baseCurrency,
                    fontSize = 26.sp,
                    color = AppTheme.colors.textColorPrimary
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.weight(weight1),
                        text = "Change 24h:",
                        fontSize = 16.sp,
                        color = AppTheme.colors.textColorSecondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        modifier = Modifier.weight(weight2),
                        text = it.priceChange24h.toPlainString(),
                        fontSize = 16.sp,
                        color = if (it.priceChange24h > BigDecimal.ZERO) {
                            AppTheme.colors.chartGreen
                        } else {
                            AppTheme.colors.chartRed
                        }
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.weight(weight1),
                        text = "Change 24h %:",
                        fontSize = 16.sp,
                        color = AppTheme.colors.textColorSecondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        modifier = Modifier.weight(weight2),
                        text = it.priceChangePercentage24h.toPlainString() + "%",
                        fontSize = 16.sp,
                        color = if (it.priceChangePercentage24h > BigDecimal.ZERO) {
                            AppTheme.colors.chartGreen
                        } else {
                            AppTheme.colors.chartRed
                        }
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.weight(weight1),
                        text = "High 24h:",
                        fontSize = 16.sp,
                        color = AppTheme.colors.textColorSecondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        modifier = Modifier.weight(weight2),
                        text = it.high24h.toPlainString(),
                        fontSize = 16.sp,
                        color = AppTheme.colors.textColorPrimary
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.weight(weight1),
                        text = "Low 24h:",
                        fontSize = 16.sp,
                        color = AppTheme.colors.textColorSecondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        modifier = Modifier.weight(weight2),
                        text = it.low24h.toPlainString(),
                        fontSize = 16.sp,
                        color = AppTheme.colors.textColorPrimary
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.weight(weight1),
                        text = "Total Volume",
                        fontSize = 16.sp,
                        color = AppTheme.colors.textColorSecondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        modifier = Modifier.weight(weight2),
                        text = it.totalVolume.toPlainString(),
                        fontSize = 16.sp,
                        color = AppTheme.colors.textColorPrimary
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.weight(weight1),
                        text = "Market Cap rank:",
                        fontSize = 16.sp,
                        color = AppTheme.colors.textColorSecondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        modifier = Modifier.weight(weight2),
                        text = it.marketCapRank.toString(),
                        fontSize = 16.sp,
                        color = AppTheme.colors.textColorPrimary
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.weight(weight1),
                        text = "Market Cap:",
                        fontSize = 16.sp,
                        color = AppTheme.colors.textColorSecondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        modifier = Modifier.weight(weight2),
                        text = it.marketCap.toString(),
                        fontSize = 16.sp,
                        color = AppTheme.colors.textColorPrimary
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
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
    val chartColor = if (chartData.isUpInThisTimeFrame) {
        AppTheme.colors.chartGreen
    } else {
        AppTheme.colors.chartRed
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