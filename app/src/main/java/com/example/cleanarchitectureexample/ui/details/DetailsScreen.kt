package com.example.cleanarchitectureexample.ui.details

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@Composable
fun DetailsScreen(viewModel: DetailsViewModel) {
    val marketInfo by viewModel.marketInfo.collectAsState()
    val chartData by viewModel.chartData.collectAsState()

    val backButtonId = remember { "backButtonId" }
    val chartId = remember { "chartId" }
    val pairNameId = remember { "pairNameId" }

    val constraints = remember {
        createConstraints(
            backButtonId = backButtonId,
            chartId = chartId,
            pairNameId = pairNameId
        )
    }

    ConstraintLayout(
        modifier = Modifier
            .background(AppTheme.colors.backgroundPrimary),
        constraintSet = constraints,
    ) {
        BackButton(
            modifier = Modifier.layoutId(backButtonId),
            onClick = { viewModel.backButtonClicked() }
        )
        chartData?.let {
            Chart(
                modifier = Modifier.layoutId(chartId),
                chartData = it
            )
        }
        marketInfo?.let {
            Text(
                modifier = Modifier.layoutId(pairNameId),
                text = it.name,
                fontSize = 32.sp,
                color = AppTheme.colors.textColorPrimary
            )
        }
    }
}

private fun createConstraints(
    backButtonId: String,
    chartId: String,
    pairNameId: String
): ConstraintSet {
    return ConstraintSet {
        val backButton = createRefFor(backButtonId)
        val chart = createRefFor(chartId)
        val pairName = createRefFor(pairNameId)

        constrain(backButton) {
            top.linkTo(parent.top, 8.dp)
            start.linkTo(parent.start, 8.dp)
        }

        constrain(chart) {
            top.linkTo(backButton.bottom, 16.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)

            height = Dimension.fillToConstraints
            width = Dimension.ratio("3:1")
        }

        constrain(pairName) {
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