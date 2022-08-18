package com.example.cleanarchitectureexample.ui.details

import com.example.cleanarchitectureexample.ui.base.BaseViewModel
import com.example.cleanarchitectureexample.ui.base.EventChannel
import com.example.cleanarchitectureexample.ui.base.asFlow
import com.example.cleanarchitectureexample.ui.base.viewModelLaunch
import com.example.domain.model.CurrencyCode
import com.example.domain.usecase.GetMarketChartDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class DetailsViewModel(
    private val currencyId: String,
    private val getMarketChartDataUseCase: GetMarketChartDataUseCase
) : BaseViewModel() {

    private val _navigation = EventChannel<Navigation>()
    val navigation = _navigation.asFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _chartData = MutableStateFlow(listOf(ChartPoint(0f, 0f),ChartPoint(0f, 0f)))
    val chartData = _chartData.asStateFlow()

    init {
        _name.value = currencyId

        viewModelLaunch {
            val data = getMarketChartDataUseCase.execute(
                currencyId = currencyId,
                baseCurrency = CurrencyCode.Pln
            )

            val max = data.maxBy { it.price }.price
            val min = data.minBy { it.price }.price
            _chartData.value = data.mapIndexed { index, marketChartPrice ->
                ChartPoint(
                    x = index.toFloat(),
                    y = (marketChartPrice.price - min).toFloat()
                )
            }.onEach {
                Timber.d("ELOELO $it $min $max diff: ${max - min}}")
            }
        }
    }

    fun backButtonClicked() {
        _navigation.trySend(Navigation.Back)
    }

    sealed class Navigation {
        object Back : Navigation()
    }

}