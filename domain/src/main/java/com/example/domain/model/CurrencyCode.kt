package com.example.domain.model

enum class CurrencyCode(val rawValue: String) {

    Pln("pln"),
    Usd("usd"),
    Eur("eur"),
    Btc("btc");

    companion object {
        fun fromRawValue(rawValue: String): CurrencyCode? {
            return values().firstOrNull { it.rawValue == rawValue }
        }
    }

}