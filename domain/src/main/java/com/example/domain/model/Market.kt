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
    val high24h: BigDecimal,
    val low24h: BigDecimal,
    val priceChange24h: BigDecimal,
    val priceChangePercentage24h: BigDecimal
)