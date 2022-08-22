package com.example.domain.repository

import com.example.domain.model.CurrencyCode
import com.example.domain.model.Market
import com.example.domain.model.MarketChartPrice
import kotlinx.coroutines.flow.Flow

interface MarketRepository {

    fun observeMarkets(baseCurrency: CurrencyCode): Flow<List<Market>?>

    suspend fun getMarketChartData(
        currencyId: String,
        baseCurrency: CurrencyCode
    ): List<MarketChartPrice>

    suspend fun getMarket(currencyId: String): Market

}