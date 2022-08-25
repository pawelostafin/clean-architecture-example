package com.example.cleanarchitectureexample.ui.settings

import app.cash.turbine.test
import com.example.domain.model.DarkThemeMode
import com.example.domain.provider.DispatchersProvider
import com.example.domain.usecase.ObserveDarkThemeModeUseCase
import com.example.domain.usecase.SetDarkThemeModeUseCase
import io.mockk.coEvery
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.dsl.module
import org.koin.test.KoinTestRule

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest : BaseTest() {

    private var _SUT: SettingsViewModel? = null
    private val SUT: SettingsViewModel
        get() = _SUT!!

    private lateinit var observeDarkThemeModeUseCaseMock: ObserveDarkThemeModeUseCase
    private lateinit var setDarkThemeModeUseCaseMock: SetDarkThemeModeUseCase

    override fun setUp() {
        super.setUp()

        _SUT = null
        observeDarkThemeModeUseCaseMock = mockk()
        setDarkThemeModeUseCaseMock = mockk()

        setThemeMode(DarkThemeMode.AUTO)
    }

    override fun tearDown() {
        super.tearDown()

        verify(atLeast = 1) { observeDarkThemeModeUseCaseMock.execute() }
        confirmVerified(observeDarkThemeModeUseCaseMock)
        confirmVerified(setDarkThemeModeUseCaseMock)
    }

    @Test
    fun `When backButtonClicked() method is invoked, Back navigation is emitted`() = runTest {
        createSut()

        SUT.navigation.test {
            SUT.backButtonClicked()
            val item = awaitItem()
            assertEquals(SettingsViewModel.Navigation.Back, item)
        }
    }

    @Test
    fun `StateFlow's Initial values are correct`() = runTest {
        val initialDarkThemeMode = DarkThemeMode.AUTO
        setThemeMode(initialDarkThemeMode)

        createSut(initialize = false)

        verify(exactly = 1) { observeDarkThemeModeUseCaseMock.execute() }
        assertEquals(initialDarkThemeMode, SUT.darkThemeMode.value)
        assertEquals(false, SUT.darkThemeModeDropdownVisibility.value)
    }

    @Test
    fun `When darkThemeItemClicked() is invoked, darkThemeModeDropdownVisibility changes to true`() = runTest {
        createSut()

        SUT.darkThemeModeDropdownVisibility.test {
            assertEquals(false, awaitItem())
            SUT.darkThemeItemClicked()
            assertEquals(true, awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun `When darkThemeDropdownDismissRequested() is invoked, darkThemeModeDropdownVisibility changes from true to false`() = runTest {
        createSut()

        SUT.darkThemeModeDropdownVisibility.test {
            assertEquals(false, awaitItem())
            SUT.darkThemeItemClicked()
            assertEquals(true, awaitItem())
            SUT.darkThemeDropdownDismissRequested()
            assertEquals(false, awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun `When darkThemeModeChangeRequested() is invoked, setDarkThemeModeUseCase is invoked with correct value and darkThemeModeDropdownVisibility changes from true to false`() =
        runTest {
            coEvery { setDarkThemeModeUseCaseMock.execute(any()) } returns Unit
            createSut()

            val newValue = DarkThemeMode.DISABLED

            SUT.darkThemeModeDropdownVisibility.test {
                assertEquals(false, awaitItem())
                SUT.darkThemeItemClicked()
                assertEquals(true, awaitItem())
                SUT.darkThemeModeChangeRequested(newValue)
                assertEquals(false, awaitItem())
                expectNoEvents()
            }

            verify(exactly = 1) { setDarkThemeModeUseCaseMock.execute(newValue) }
        }

    @Test
    fun `When DarkThemeMode is updated, its emitted to view`() = runTest {
        val fakeFlow = MutableStateFlow(DarkThemeMode.DISABLED)
        every { observeDarkThemeModeUseCaseMock.execute() } returns fakeFlow

        createSut()

        SUT.darkThemeMode.test {
            assertEquals(DarkThemeMode.DISABLED, awaitItem())

            fakeFlow.value = DarkThemeMode.AUTO
            assertEquals(DarkThemeMode.AUTO, awaitItem())

            fakeFlow.value = DarkThemeMode.ENABLED
            assertEquals(DarkThemeMode.ENABLED, awaitItem())

            fakeFlow.value = DarkThemeMode.DISABLED
            assertEquals(DarkThemeMode.DISABLED, awaitItem())
        }
        verify(exactly = 2) { observeDarkThemeModeUseCaseMock.execute() }
    }

    private fun setThemeMode(darkThemeMode: DarkThemeMode) {
        every { observeDarkThemeModeUseCaseMock.execute() } returns MutableStateFlow(darkThemeMode)
    }

    private fun createSut(initialize: Boolean = true) {
        _SUT = SettingsViewModel(
            observeDarkThemeModeUseCase = observeDarkThemeModeUseCaseMock,
            setDarkThemeModeUseCase = setDarkThemeModeUseCaseMock
        )
        if (initialize) {
            SUT.initializeIfNeeded()
        }
    }

}

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseTest {


    @Before
    open fun setUp() {
    }

    @After
    open fun tearDown() {
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            TestsModule.coroutineModule
        )
    }

}

// Reusable JUnit4 TestRule to override the Main dispatcher
@ExperimentalCoroutinesApi
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

object TestsModule {

    val coroutineModule = module {
        factory<DispatchersProvider> { TestDispatchersProvider() }
    }

}

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatchersProvider : DispatchersProvider {

    override val Main: CoroutineDispatcher = UnconfinedTestDispatcher()
    override val IO: CoroutineDispatcher = UnconfinedTestDispatcher()
    override val Default: CoroutineDispatcher = UnconfinedTestDispatcher()
    override val Unconfined: CoroutineDispatcher = UnconfinedTestDispatcher()

}