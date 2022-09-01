package com.example.cleanarchitectureexample

import app.cash.turbine.test
import com.example.cleanarchitectureexample.ui.settings.BaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class TurbineValidTest : BaseTest() {

    @Test
    fun `turbine valid test`() = runTest {
        val flow = MutableStateFlow(false)

        flow.test {
            Assert.assertEquals(false, awaitItem())
            flow.value = true
            assertFailsWith<AssertionError> {
                expectNoEvents()
            }
        }
    }

}