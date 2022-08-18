package com.example.domain.model

import java.math.BigDecimal

data class Market(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String?,
    val currentPrice: BigDecimal,
    val marketCap: Long,
    val marketCapRank: Long,
    val totalVolume: BigDecimal,
    val high_24: BigDecimal,
    val low_24: BigDecimal,
    val priceChange24h: BigDecimal,
    val priceChangePercentage24: BigDecimal
)