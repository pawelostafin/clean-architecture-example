package com.example.data.repository

import android.content.SharedPreferences
import com.example.domain.model.CurrencyCode
import com.example.domain.repository.BaseCurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class BaseCurrencyRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : BaseCurrencyRepository {

    private val _baseCurrency by lazy {
        MutableStateFlow(getInitialValue())
    }

    override fun observe(): Flow<CurrencyCode> {
        return _baseCurrency
    }

    override fun set(baseCurrencyCode: CurrencyCode) {
        saveBaseCurrency(baseCurrencyCode)
        _baseCurrency.value = baseCurrencyCode
    }

    override suspend fun get(): CurrencyCode {
        return _baseCurrency.value
    }

    private fun getInitialValue(): CurrencyCode {
        val default = CurrencyCode.Pln
        val savedValue = getSavedValue()
        return savedValue ?: default
    }

    private fun getSavedValue(): CurrencyCode? {
        val raw = sharedPreferences.getString(KEY_BASE_CURRENCY, null)
        return raw?.let { CurrencyCode.fromRawValue(it) }
    }

    private fun saveBaseCurrency(baseCurrencyCode: CurrencyCode) {
        sharedPreferences.edit()
            .putString(KEY_BASE_CURRENCY, baseCurrencyCode.rawValue)
            .apply()
    }

    companion object {
        private const val KEY_BASE_CURRENCY = "KEY_BASE_CURRENCY"
    }

}