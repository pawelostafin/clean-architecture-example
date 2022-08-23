package com.example.domain.usecase

import com.example.domain.model.CurrencyCode
import com.example.domain.repository.BaseCurrencyRepository

class SetBaseCurrencyUseCase(
    private val baseCurrencyRepository: BaseCurrencyRepository
) {

    suspend fun execute(baseCurrency: CurrencyCode) {
        baseCurrencyRepository.set(baseCurrency)
    }

}