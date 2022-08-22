package com.example.domain.usecase

import com.example.domain.model.Market
import com.example.domain.repository.MarketRepository

class GetMarketUseCase(
    private val marketRepository: MarketRepository
) {

    suspend fun execute(currencyId: String): Market {
        return marketRepository.getMarket(
            currencyId = currencyId
        )
    }

}