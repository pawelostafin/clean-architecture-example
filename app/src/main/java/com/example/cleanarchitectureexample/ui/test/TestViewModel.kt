package com.example.cleanarchitectureexample.ui.test

import com.example.cleanarchitectureexample.ui.base.BaseViewModel
import com.example.cleanarchitectureexample.ui.base.EventChannel
import com.example.cleanarchitectureexample.ui.base.asFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TestViewModel : BaseViewModel() {

    private val _navigation = EventChannel<Navigation>()
    val navigation = _navigation.asFlow()

    private val _text = MutableStateFlow("")
    val text = _text.asStateFlow()

    private val _backgroundColor = MutableStateFlow(RgbColor.random())
    val backgroundColor = _backgroundColor.asStateFlow()

    override fun initialize() {
        super.initialize()
    }

    fun testButtonClicked() {
        emitRandomNumber()
    }

    fun backButtonClicked() {
        _navigation.trySend(Navigation.Back)
    }

    fun navigateButtonClicked() {
        _navigation.trySend(Navigation.Next)
    }

    fun changeBackgroundButtonClicked() {
        emitRandomColor()
    }

    private fun emitRandomColor() {
        _backgroundColor.value = RgbColor.random()
    }

    private fun emitRandomNumber() {
        val randomNumber = (0..100).shuffled().take(4).joinToString()
        _text.value = randomNumber
    }

    sealed class Navigation {
        object Back : Navigation()
        object Next : Navigation()
    }

}

data class RgbColor(
    val red: Int,
    val green: Int,
    val blue: Int
) {
    companion object {

        fun random(): RgbColor {
            val colorRange = (0..255)
            return RgbColor(
                red = colorRange.random(),
                green = colorRange.random(),
                blue = colorRange.random()
            )
        }

    }
}