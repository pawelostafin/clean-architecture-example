package com.example.domain.usecase

import com.example.domain.model.CurrencyCode
import com.example.domain.model.MarketChartPrice
import com.example.domain.repository.MarketRepository

class GetMarketChartDataUseCase(
    private val marketRepository: MarketRepository
) {

    suspend fun execute(
        currencyId: String,
        baseCurrency: CurrencyCode
    ): List<MarketChartPrice> {
        return marketRepository.getMarketChartData(
            currencyId = currencyId,
            baseCurrency = baseCurrency
        )
    }

}