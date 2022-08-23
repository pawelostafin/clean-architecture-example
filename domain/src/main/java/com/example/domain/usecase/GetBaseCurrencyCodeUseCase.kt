package com.example.domain.usecase

import com.example.domain.model.CurrencyCode
import com.example.domain.repository.BaseCurrencyRepository

class GetBaseCurrencyCodeUseCase(
    private val baseCurrencyRepository: BaseCurrencyRepository
) {

    suspend fun execute(): CurrencyCode {
        return baseCurrencyRepository.get()
    }

}