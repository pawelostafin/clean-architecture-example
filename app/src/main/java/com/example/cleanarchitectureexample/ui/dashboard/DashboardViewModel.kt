package com.example.cleanarchitectureexample.ui.dashboard

import com.example.cleanarchitectureexample.ui.base.BaseViewModel
import com.example.cleanarchitectureexample.ui.base.EventChannel
import com.example.cleanarchitectureexample.ui.base.asFlow
import com.example.cleanarchitectureexample.ui.base.viewModelLaunch
import com.example.cleanarchitectureexample.ui.base.viewModelObserveFlow
import com.example.domain.model.CurrencyCode
import com.example.domain.usecase.GetUserDetailsUseCase
import com.example.domain.usecase.ObserveBaseCurrencyUseCase
import com.example.domain.usecase.ObserveMarketsUseCase
import com.example.domain.usecase.SetBaseCurrencyUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.withContext
import java.math.BigDecimal

class DashboardViewModel(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val observeMarketsUseCase: ObserveMarketsUseCase,
    private val observeBaseCurrencyUseCase: ObserveBaseCurrencyUseCase,
    private val setBaseCurrencyUseCase: SetBaseCurrencyUseCase
) : BaseViewModel() {

    private val _navigation = EventChannel<Navigation>()
    val navigation = _navigation.asFlow()

    private val _profileButtonState = MutableStateFlow<ProfileButtonState>(ProfileButtonState.InProgress)
    val profileButtonState = _profileButtonState.asStateFlow()

    private val _items = MutableStateFlow<List<MarketItem>?>(null)
    val items = _items.asStateFlow()

    private val _progressViewVisibility = MutableStateFlow(false)
    val progressViewVisibility = _progressViewVisibility.asStateFlow()

    private val _baseCurrencySelectorState = MutableStateFlow<BaseCurrencySelectorState?>(null)
    val baseCurrencySelectorState = _baseCurrencySelectorState.asStateFlow()

    override fun initialize() {
        super.initialize()

        getUserDetails()
        observeMarkets()
        observeBaseCurrency()
    }

    private fun observeBaseCurrency() {
        viewModelObserveFlow(
            flowProvider = { observeBaseCurrencyUseCase.execute() },
            onEach = {
                val newValue = getNewBaseCurrencySelectorValue(it)
                _baseCurrencySelectorState.value = newValue
            }
        )
    }

    private fun getNewBaseCurrencySelectorValue(
        newBaseCurrencyCode: CurrencyCode
    ): BaseCurrencySelectorState {
        val currentSelectorValue = _baseCurrencySelectorState.value
        val initialValue = BaseCurrencySelectorState(
            baseCurrency = newBaseCurrencyCode,
            baseCurrencyOptions = CurrencyCode.values().toList(),
            isDropdownVisible = false
        )
        return currentSelectorValue
            ?.copy(baseCurrency = newBaseCurrencyCode)
            ?: initialValue
    }

    private fun observeMarkets() {
        viewModelObserveFlow(
            onProgressChanged = { _progressViewVisibility.value = it },
            flowProvider = {
                observeBaseCurrencyUseCase.execute()
                    .flatMapLatest {
                        observeMarketsUseCase.execute(baseCurrency = it)
                    }
            },
        ) { markets ->
            val items = withContext(Dispatchers.IO) {
                markets?.map {
                    MarketItem(
                        id = it.id,
                        name = it.symbol.uppercase(),
                        imageUrl = it.image,
                        price = it.currentPrice
                    )
                }
            }
            _items.value = items
            _progressViewVisibility.value = items == null
        }
    }

    private fun getUserDetails() {
        viewModelLaunch(
            onProgressChanged = { isInProgress ->
                if (isInProgress) {
                    _profileButtonState.value = ProfileButtonState.InProgress
                }
            },
            onError = ::showErrorDialog,
        ) {
            val userDetails = getUserDetailsUseCase.execute()
            val profileButtonState = ProfileButtonState.Data(
                imageUrl = userDetails.imageUrl,
                firstLetterOfFirstName = userDetails.firstLetterOfFirstName
            )
            _profileButtonState.value = profileButtonState
        }
    }

    fun backButtonClicked() {
        _navigation.trySend(Navigation.Back)
    }

    fun profileButtonClicked() {
        _navigation.trySend(Navigation.Profile)
    }

    fun itemClicked(item: MarketItem) {
        _navigation.trySend(Navigation.Details(item.id))
    }

    fun baseCurrencySelectorClicked() {
        val currentValue = _baseCurrencySelectorState.value
        _baseCurrencySelectorState.value = currentValue?.copy(
            isDropdownVisible = true
        )
    }

    fun baseCurrencySelectorDismissRequested() {
        val currentValue = _baseCurrencySelectorState.value
        _baseCurrencySelectorState.value = currentValue?.copy(
            isDropdownVisible = false
        )
    }

    fun baseCurrencyChangeRequested(newBaseCurrency: CurrencyCode) {
        viewModelLaunch {
            setBaseCurrencyUseCase.execute(baseCurrency = newBaseCurrency)
        }
        val currentValue = _baseCurrencySelectorState.value
        _baseCurrencySelectorState.value = currentValue?.copy(
            isDropdownVisible = false
        )
    }

    sealed class Navigation {
        object Back : Navigation()
        object Profile : Navigation()
        data class Details(val currencyId: String) : Navigation()
    }

}

sealed class ProfileButtonState {

    object InProgress : ProfileButtonState()

    data class Data(
        val imageUrl: String?,
        val firstLetterOfFirstName: String
    ) : ProfileButtonState()

}

data class MarketItem(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val price: BigDecimal
)

data class BaseCurrencySelectorState(
    val baseCurrency: CurrencyCode,
    val baseCurrencyOptions: List<CurrencyCode>,
    val isDropdownVisible: Boolean
)