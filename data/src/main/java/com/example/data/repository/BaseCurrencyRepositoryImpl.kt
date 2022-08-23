package com.example.data.repository

import android.content.SharedPreferences
import com.example.domain.model.CurrencyCode
import com.example.domain.repository.BaseCurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class BaseCurrencyRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : BaseCurrencyRepository {

    private val _baseCurrency = MutableStateFlow<CurrencyCode?>(null)

    override fun observe(): Flow<CurrencyCode?> {
        if (_baseCurrency.value == null) {
            val default = CurrencyCode.Pln
            val savedValue = getSavedValue()
            _baseCurrency.value = savedValue ?: default
        }
        return _baseCurrency
    }

    override fun set(baseCurrencyCode: CurrencyCode) {
        saveBaseCurrency(baseCurrencyCode)
        _baseCurrency.value = baseCurrencyCode
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