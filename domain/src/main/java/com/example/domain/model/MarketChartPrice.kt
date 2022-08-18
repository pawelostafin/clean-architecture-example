package com.example.domain.model

import java.math.BigDecimal

data class MarketChartPrice(
    val timestamp: Long,
    val price: BigDecimal
)