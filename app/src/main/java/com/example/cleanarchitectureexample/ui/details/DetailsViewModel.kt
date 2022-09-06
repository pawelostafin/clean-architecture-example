package com.example.cleanarchitectureexample.ui.details

import com.example.cleanarchitectureexample.ui.base.ComposeBaseViewModel
import com.example.cleanarchitectureexample.ui.base.viewModelLaunch
import com.example.domain.model.ChartData
import com.example.domain.model.CurrencyCode
import com.example.domain.usecase.GetBaseCurrencyCodeUseCase
import com.example.domain.usecase.GetMarketChartDataUseCase
import com.example.domain.usecase.GetMarketUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.math.BigDecimal

class DetailsViewModel(
    private val currencyId: String,
    private val getMarketChartDataUseCase: GetMarketChartDataUseCase,
    private val getMarketUseCase: GetMarketUseCase,
    private val getBaseCurrencyCodeUseCase: GetBaseCurrencyCodeUseCase
) : ComposeBaseViewModel<DetailsViewModel.Navigation>() {

    private val _marketInfo = MutableStateFlow<MarketInfo?>(null)
    val marketInfo = _marketInfo.asStateFlow()

    private val _chartData = MutableStateFlow<ChartData?>(null)
    val chartData = _chartData.asStateFlow()

    init {
        getMarketInfo()
        getMarketChartData()
    }

    fun backButtonClicked() {
        _navigation.trySend(Navigation.Back)
    }

    private fun getMarketInfo() {
        viewModelLaunch {
            val market = getMarketUseCase.execute(currencyId = currencyId)
            val baseCurrency = getBaseCurrencyCodeUseCase.execute()
            _marketInfo.value = MarketInfo(
                name = market.name,
                imageUrl = market.image,
                currentPrice = market.currentPrice,
                baseCurrency = baseCurrency,
                high24h = market.high24h,
                low24h = market.low24h,
                priceChange24h = market.priceChange24h,
                priceChangePercentage24h = market.priceChangePercentage24h,
                totalVolume = market.totalVolume,
                marketCap = market.marketCap,
                marketCapRank = market.marketCapRank
            )
        }
    }

    private fun getMarketChartData() {
        viewModelLaunch {
            _chartData.value = getMarketChartDataUseCase.execute(currencyId = currencyId)
        }
    }

    sealed class Navigation {
        object Back : Navigation()
    }

    override fun systemBackButtonClicked() {
        _navigation.trySend(Navigation.Back)
    }

}

data class MarketInfo(
    val baseCurrency: CurrencyCode,
    val name: String,
    val imageUrl: String?,
    val currentPrice: BigDecimal,
    val high24h: BigDecimal,
    val low24h: BigDecimal,
    val priceChange24h: BigDecimal,
    val priceChangePercentage24h: BigDecimal,
    val totalVolume: BigDecimal,
    val marketCap: Long,
    val marketCapRank: Long
)