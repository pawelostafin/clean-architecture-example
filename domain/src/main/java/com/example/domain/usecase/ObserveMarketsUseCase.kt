package com.example.domain.usecase

import com.example.domain.model.CurrencyCode
import com.example.domain.model.Market
import com.example.domain.repository.MarketRepository
import kotlinx.coroutines.flow.Flow

class ObserveMarketsUseCase(
    private val marketRepository: MarketRepository
) {

    fun execute(baseCurrency: CurrencyCode): Flow<List<Market>?> {
        return marketRepository.observeMarkets(baseCurrency = baseCurrency)
    }

}