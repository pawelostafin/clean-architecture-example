package com.example.cleanarchitectureexample.ui.details

import com.example.cleanarchitectureexample.ui.base.BaseViewModel
import com.example.cleanarchitectureexample.ui.base.EventChannel
import com.example.cleanarchitectureexample.ui.base.asFlow
import com.example.cleanarchitectureexample.ui.base.viewModelLaunch
import com.example.domain.model.ChartData
import com.example.domain.usecase.GetMarketChartDataUseCase
import com.example.domain.usecase.GetMarketUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class DetailsViewModel(
    private val currencyId: String,
    private val getMarketChartDataUseCase: GetMarketChartDataUseCase,
    private val getMarketUseCase: GetMarketUseCase
) : BaseViewModel() {

    private val _navigation = EventChannel<Navigation>()
    val navigation = _navigation.asFlow()

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
            _marketInfo.value = MarketInfo(name = market.name)
        }
    }

    private fun getMarketChartData() {
        viewModelLaunch {
            _chartData.value = getMarketChartDataUseCase.execute(currencyId = currencyId).also {
                Timber.d("ELOELO $it")
            }
        }
    }

    sealed class Navigation {
        object Back : Navigation()
    }

}

data class MarketInfo(
    val name: String
)