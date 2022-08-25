package com.example.data.repository

import com.example.data.network.model.toDomainModel
import com.example.data.network.service.MarketService
import com.example.domain.model.CurrencyCode
import com.example.domain.model.Market
import com.example.domain.model.MarketChartPrice
import com.example.domain.provider.DispatchersProvider
import com.example.domain.repository.MarketRepository
import com.example.domain.util.ReusableCoroutineScope
import com.example.domain.util.supportLaunch
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import timber.log.Timber

class MarketRepositoryImpl(
    private val marketService: MarketService,
    private val dispatchersProvider: DispatchersProvider
) : MarketRepository {

    @Volatile
    private var currentBaseCurrency: CurrencyCode? = null

    private val _markets = MutableStateFlow<List<Market>?>(null)

    @Volatile
    private var periodicCallsScope = ReusableCoroutineScope()

    override fun observeMarkets(baseCurrency: CurrencyCode): Flow<List<Market>?> {
        if (baseCurrency != currentBaseCurrency) {
            _markets.value = null
            startPeriodicCalls(baseCurrency)
        }
        return _markets
    }

    override suspend fun getMarketChartData(
        currencyId: String,
        baseCurrency: CurrencyCode
    ): List<MarketChartPrice> {
        return withContext(dispatchersProvider.IO) {
            val response = marketService.getMarketChartData(
                id = currencyId,
                vsCurrency = baseCurrency.rawValue
            )
            response.prices.map {
                MarketChartPrice(
                    timestamp = it[0].toLong(),
                    price = it[1].toBigDecimal()
                )
            }
        }
    }

    override suspend fun getMarket(currencyId: String): Market {
        return _markets.value!!.first { it.id == currencyId }
    }

    private fun startPeriodicCalls(baseCurrencyCode: CurrencyCode) {
        periodicCallsScope.cancelAndReset()
        periodicCallsScope.supportLaunch {
            while (true) {
                runCatching {
                    val response = marketService.getMarkets(vsCurrency = baseCurrencyCode.rawValue)
                    _markets.value = response.map { it.toDomainModel() }
                }.onFailure {
                    Timber.e(it)
                }
                delay(10_000L)
            }
        }
    }

}