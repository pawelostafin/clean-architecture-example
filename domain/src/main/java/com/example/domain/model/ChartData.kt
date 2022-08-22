package com.example.domain.model

data class ChartData(
    val points: List<ChartPoint>,
    val isUpInThisTimeFrame: Boolean,
    val maxY: Float,
    val minY: Float
)

data class ChartPoint(
    val x: Float,
    val y: Float
)