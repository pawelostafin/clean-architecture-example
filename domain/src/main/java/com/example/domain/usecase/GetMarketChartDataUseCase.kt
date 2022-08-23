package com.example.domain.usecase

import com.example.domain.model.ChartData
import com.example.domain.model.ChartDataTrend
import com.example.domain.model.ChartPoint
import com.example.domain.model.CurrencyCode
import com.example.domain.repository.MarketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigDecimal

class GetMarketChartDataUseCase(
    private val marketRepository: MarketRepository
) {

    suspend fun execute(currencyId: String): ChartData {
        return withContext(Dispatchers.IO) {
            val pricesInTime = marketRepository.getMarketChartData(
                currencyId = currencyId,
                baseCurrency = CurrencyCode.Pln
            )
            val minPrice = pricesInTime.minBy { it.price }.price

            val priceDifference = pricesInTime.last().price - pricesInTime.first().price
            val trend = when {
                priceDifference > BigDecimal.ZERO -> ChartDataTrend.UP
                priceDifference < BigDecimal.ZERO -> ChartDataTrend.DOWN
                else -> ChartDataTrend.EQUAL
            }

            val points = pricesInTime.mapIndexed { index, marketChartPrice ->
                ChartPoint(
                    x = index.toFloat(),
                    y = (marketChartPrice.price - minPrice).toFloat()
                )
            }
            ChartData(
                points = points,
                trend = trend,
                maxY = points.maxOf { it.y },
                minY = points.minOf { it.y }
            )
        }
    }

}