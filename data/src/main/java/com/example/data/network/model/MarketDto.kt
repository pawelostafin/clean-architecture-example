package com.example.data.network.model

import com.example.domain.model.Market
import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class MarketDto(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String?,
    val current_price: BigDecimal,
    val market_cap: Long,
    val market_cap_rank: Long,
    val total_volume: BigDecimal,
    val high_24h: BigDecimal,
    val low_24h: BigDecimal,
    val price_change_24h: BigDecimal,
    val price_change_percentage_24h: BigDecimal
)

fun MarketDto.toDomainModel(): Market {
    return Market(
        id = id,
        symbol = symbol,
        name = name,
        image = image,
        currentPrice = current_price,
        marketCap = market_cap,
        marketCapRank = market_cap_rank,
        totalVolume = total_volume,
        high24h = high_24h,
        low24h = low_24h,
        priceChange24h = price_change_24h,
        priceChangePercentage24h = price_change_percentage_24h,
    )
}