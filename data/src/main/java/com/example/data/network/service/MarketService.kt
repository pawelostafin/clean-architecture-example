package com.example.data.network.service

import com.example.data.network.model.MarketDto
import com.squareup.moshi.JsonClass
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarketService {

    @GET("coins/markets")
    suspend fun getMarkets(
        @Query("vs_currency") vsCurrency: String
    ): List<MarketDto>

    @GET("coins/{id}/market_chart?days=1")
    suspend fun getMarketChartData(
        @Path("id") id: String,
        @Query("vs_currency") vsCurrency: String
    ): MarketChartDataDto

}

@JsonClass(generateAdapter = true)
data class MarketChartDataDto(
    val prices: List<List<String>>
)