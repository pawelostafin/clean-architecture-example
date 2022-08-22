package com.example.cleanarchitectureexample.ui.dashboard

import com.example.cleanarchitectureexample.ui.base.BaseViewModel
import com.example.cleanarchitectureexample.ui.base.EventChannel
import com.example.cleanarchitectureexample.ui.base.asFlow
import com.example.cleanarchitectureexample.ui.base.viewModelLaunch
import com.example.cleanarchitectureexample.ui.base.viewModelObserveFlow
import com.example.domain.model.CurrencyCode
import com.example.domain.usecase.GetUserDetailsUseCase
import com.example.domain.usecase.ObserveMarketsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.math.BigDecimal

class DashboardViewModel(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val observeMarketsUseCase: ObserveMarketsUseCase
) : BaseViewModel() {

    private val _navigation = EventChannel<Navigation>()
    val navigation = _navigation.asFlow()

    private val _profileButtonState = MutableStateFlow<ProfileButtonState>(ProfileButtonState.InProgress)
    val profileButtonState = _profileButtonState.asStateFlow()

    private val _items = MutableStateFlow<List<MarketItem>?>(null)
    val items = _items.asStateFlow()

    private val _progressViewVisibility = MutableStateFlow(false)
    val progressViewVisibility = _progressViewVisibility.asStateFlow()

    override fun initialize() {
        super.initialize()

        getUserDetails()
        observeMarkets()
    }

    private fun observeMarkets() {
        val baseCurrency = CurrencyCode.Pln
        viewModelObserveFlow(
            onProgressChanged = { _progressViewVisibility.value = it },
            flowProvider = { observeMarketsUseCase.execute(baseCurrency = baseCurrency) },
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