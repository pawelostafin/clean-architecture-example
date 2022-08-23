package com.example.domain.repository

import com.example.domain.model.CurrencyCode
import kotlinx.coroutines.flow.Flow

interface BaseCurrencyRepository {

    fun observe(): Flow<CurrencyCode?>

    fun set(baseCurrencyCode: CurrencyCode)

}