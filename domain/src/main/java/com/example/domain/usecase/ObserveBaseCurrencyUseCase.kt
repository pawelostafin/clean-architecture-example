package com.example.domain.usecase

import com.example.domain.model.CurrencyCode
import com.example.domain.repository.BaseCurrencyRepository
import kotlinx.coroutines.flow.Flow

class ObserveBaseCurrencyUseCase(
    private val baseCurrencyRepository: BaseCurrencyRepository
) {

    fun execute(): Flow<CurrencyCode?> {
        return baseCurrencyRepository.observe()
    }

}