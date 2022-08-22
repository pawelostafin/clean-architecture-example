package com.example.domain.usecase

import com.example.domain.model.ChartData
import com.example.domain.model.ChartPoint
import com.example.domain.model.CurrencyCode
import com.example.domain.repository.MarketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetMarketChartDataUseCase(
    private val marketRepository: MarketRepository
) {

    suspend fun execute(currencyId: String): ChartData {
        return withContext(Dispatchers.IO) {
            val pricesInTime = marketRepository.getMarketChartData(
                currencyId = currencyId,
                baseCurrency = CurrencyCode.Pln
            )
            val maxPrice = pricesInTime.maxBy { it.price }.price
            val minPrice = pricesInTime.minBy { it.price }.price

            val isUpInThisTimeFrame = pricesInTime.first().price < maxPrice

            val points = pricesInTime.mapIndexed { index, marketChartPrice ->
                ChartPoint(
                    x = index.toFloat(),
                    y = (marketChartPrice.price - minPrice).toFloat()
                )
            }
            ChartData(
                points = points,
                isUpInThisTimeFrame = isUpInThisTimeFrame,
                maxY = points.maxOf { it.y },
                minY = points.minOf { it.y }
            )
        }
    }

}