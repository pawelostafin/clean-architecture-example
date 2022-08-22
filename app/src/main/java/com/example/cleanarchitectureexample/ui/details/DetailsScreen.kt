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
import androidx.compose.ui.graphics.drawscope.DrawScope
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
import com.example.cleanarchitectureexample.ui.utli.button.gestureRecognizer
import com.example.cleanarchitectureexample.ui.utli.withAlpha
import timber.log.Timber
import kotlin.math.abs

@Composable
fun DetailsScreen(viewModel: DetailsViewModel) {
    val name by viewModel.name.collectAsState()
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
        Chart(
            modifier = Modifier.layoutId(chartId),
            chartData = chartData
        )
        Text(
            modifier = Modifier.layoutId(pairNameId),
            text = name,
            fontSize = 32.sp,
            color = AppTheme.colors.textColorPrimary
        )
    }
}

private fun createChartData(): List<ChartPoint> {
    return listOf(
        ChartPoint(
            x = 0f,
            y = 0f
        ),
        ChartPoint(
            x = 1f,
            y = 5f
        ),
        ChartPoint(
            x = 2f,
            y = 4f
        ),
        ChartPoint(
            x = 3f,
            y = 15f
        ),
        ChartPoint(
            x = 4f,
            y = 6f
        ),
        ChartPoint(
            x = 5f,
            y = 9f
        ),
        ChartPoint(
            x = 6f,
            y = 12f
        ),
        ChartPoint(
            x = 7f,
            y = 14f
        ),
        ChartPoint(
            x = 8f,
            y = 11f
        ),
    )
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
    chartData: List<ChartPoint>,
    modifier: Modifier = Modifier
) {
    val initialOffset = remember { Offset.Zero }
    val lineColor = Color.Red
    val axisColor = Color.LightGray
    val axisColorSecondary = Color.Gray.withAlpha(0.2f)

    val oneUnitSize = 20.dp.pxValue
    val lineWidth = 1.dp.pxValue
    val axisWidth = 2.dp.pxValue
    val axisWidthSecondary = 1.dp.pxValue
    val chartColor = remember(chartData) {
        if (chartData.first().y > chartData.last().y) {
            Color(red = 255, green = 69, blue = 58)
        } else {
            Color.Green
        }
    }
    val selectionCircleRadius = 6.dp.pxValue
    val selectionLineWidth = 2.dp.pxValue
    val selectionColor = AppTheme.colors.primary

    val lineProgress = remember { Animatable(0f) }
    val backgroundAlpha = remember { Animatable(0f) }
    val drawBackground = remember { mutableStateOf(false) }

    val easing = 0.5f

    LaunchedEffect(lineProgress) {
        lineProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                easing = CubicBezierEasing(0.5f, 0f, 0.5f, 1f),
                durationMillis = 1000,
                delayMillis = 600
            ),
        )
        drawBackground.value = true
        backgroundAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                easing = CubicBezierEasing(0.5f, 0f, 0.5f, 1f),
                durationMillis = 1000,
            )
        )
    }

    val selectionPosition = remember { mutableStateOf<Offset?>(null) }

    Canvas(
        modifier = modifier
            .gestureRecognizer(
                onClick = {
                    selectionPosition.value = it
                    Timber.d("ELOELO clicked $it")
                }
            )
    ) {
//        drawXAxis(
//            drawScope = this,
//            color = axisColor,
//            colorSecondary = axisColorSecondary,
//            canvasSize = size,
//            axisWidth = axisWidth,
//            axisWidthSecondary = axisWidthSecondary,
//            maxXValue = chartData.maxOf { it.x }
//        )
//        drawYAxis(
//            drawScope = this,
//            color = axisColor,
//            colorSecondary = axisColorSecondary,
//            canvasSize = size,
//            width = axisWidth,
//            axisWidthSecondary = axisWidthSecondary,
//            maxYValue = chartData.maxOf { it.y }
//        )
        val offsets = chartData.map {
            it.toOffset(
                canvasSize = size,
                maxXCount = chartData.size,
                maxYValue = chartData.maxOf { it.y },
                minYValue = chartData.minOf { it.y },
            )
        }
        val path = Path().apply {
            reset()

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


        val animatedPath = Path()
        PathMeasure().let {
            it.setPath(path, false)
            it.getSegment(
                startDistance = 0f,
                stopDistance = it.length * lineProgress.value,
                destination = animatedPath
            )
        }

        if (drawBackground.value) {
            drawPath(
                path = path.apply {
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    path.close()
                },
                brush = Brush.verticalGradient(
                    colors = listOf(chartColor.withAlpha(0.3f), Color.Transparent)
                ),
                alpha = backgroundAlpha.value
            )
        }

        drawPath(
            path = animatedPath,
            color = chartColor,
            style = Stroke(
                width = lineWidth,
                cap = StrokeCap.Round
            ),
        )

        selectionPosition.value?.let { selection ->
            val pointOnChart = offsets.minBy { abs(it.x - selection.x) }
            drawCircle(
                color = selectionColor,
                radius = selectionCircleRadius,
                center = pointOnChart
            )
            drawLine(
                color = selectionColor.withAlpha(0.3f),
                strokeWidth = selectionLineWidth,
                start = pointOnChart.copy(y = 0f),
                end = pointOnChart.copy(y = size.height)
            )
        }

//        chartData.take(numberOfVisiblePoints).forEachIndexed { index, chartPoint ->

//
//            val currentOffset = chartPoint.toOffset(
//                size = size,
//                sizeOfOneUnit = oneUnitSize
//            )
//
//            drawLine(
//                color = lineColor,
//                start = previousOffset,
//                end = currentOffset
//            )
//        }
    }
}

private fun drawXAxis(
    drawScope: DrawScope,
    color: Color,
    colorSecondary: Color,
    axisWidth: Float,
    axisWidthSecondary: Float,
    canvasSize: Size,
    maxXValue: Float
) {
    val oneUnitSize = canvasSize.width / maxXValue
    drawScope.drawLine(
        color = color,
        start = Offset(x = 0f, y = canvasSize.height),
        end = Offset(x = canvasSize.width, y = canvasSize.height),
        strokeWidth = axisWidth,
        cap = StrokeCap.Round
    )

    val xRange = (1..maxXValue.toInt()).toList()
    xRange.forEachIndexed { index, i ->
        drawScope.drawLine(
            color = colorSecondary,
            start = Offset(
                x = i * oneUnitSize,
                y = 0f,
            ),
            end = Offset(
                x = i * oneUnitSize,
                y = canvasSize.height
            ),
            strokeWidth = axisWidthSecondary
        )
    }
}

private fun drawYAxis(
    drawScope: DrawScope,
    color: Color,
    colorSecondary: Color,
    width: Float,
    axisWidthSecondary: Float,
    canvasSize: Size,
    maxYValue: Float
) {
    val oneUnitSize = canvasSize.height / maxYValue

    drawScope.drawLine(
        color = color,
        start = Offset(x = 0f, y = 0f),
        end = Offset(x = 0f, y = canvasSize.height),
        strokeWidth = width,
        cap = StrokeCap.Round
    )

    val yRange = (1 until maxYValue.toInt()).toList()
    yRange.forEachIndexed { index, i ->
        drawScope.drawLine(
            color = colorSecondary,
            start = Offset(
                x = 0f,
                y = i * oneUnitSize,
            ),
            end = Offset(
                x = canvasSize.width,
                y = i * oneUnitSize
            ),
            strokeWidth = axisWidthSecondary
        )
    }
}


data class ChartPoint(
    val x: Float,
    val y: Float
)

fun ChartPoint.toOffset(
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