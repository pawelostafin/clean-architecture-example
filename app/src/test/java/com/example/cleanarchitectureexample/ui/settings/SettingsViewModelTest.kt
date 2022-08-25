package com.example.cleanarchitectureexample.ui.settings

import app.cash.turbine.test
import com.example.domain.provider.DispatchersProvider
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

//    private var _SUT: SettingsViewModel? = null
//    private val SUT: SettingsViewModel
//        get() = _SUT!!
//
//    private lateinit var observeDarkThemeModeUseCaseMock: ObserveDarkThemeModeUseCase
//    private lateinit var setDarkThemeModeUseCaseMock: SetDarkThemeModeUseCase
//
//    override fun setUp() {
//        super.setUp()
//
//        _SUT = null
//        observeDarkThemeModeUseCaseMock = mockk()
//        setDarkThemeModeUseCaseMock = mockk()
//
//        setThemeMode(DarkThemeMode.AUTO)
//    }
//
//    override fun tearDown() {
//        super.tearDown()
//
//        verify(atLeast = 1) { observeDarkThemeModeUseCaseMock.execute() }
//        confirmVerified(observeDarkThemeModeUseCaseMock)
//        confirmVerified(setDarkThemeModeUseCaseMock)
//    }
//
//    @Test
//    fun `When backButtonClicked() method is invoked, Back navigation is emitted`() = runTest {
//        createAndInitializeSUT()
//
//        SUT.navigation.test {
//            SUT.backButtonClicked()
//            val item = awaitItem()
//            assertEquals(SettingsViewModel.Navigation.Back, item)
//        }
//    }
//
//    @Test
//    fun `StateFlow's Initial values are correct`() = runTest {
//        val initialDarkThemeMode = DarkThemeMode.AUTO
//        setThemeMode(initialDarkThemeMode)
//
//        createAndInitializeSUT()
//
//        verify(exactly = 1) { observeDarkThemeModeUseCaseMock.execute() }
//        assertEquals(initialDarkThemeMode, SUT.darkThemeMode.value)
//        assertEquals(false, SUT.darkThemeModeDropdownVisibility.value)
//    }
//
//    @Test
//    fun `When darkThemeItemClicked() is invoked, darkThemeModeDropdownVisibility changes to true`() = runTest {
//        createAndInitializeSUT()
//
//        SUT.darkThemeModeDropdownVisibility.test {
//            assertEquals(false, awaitItem())
//            SUT.darkThemeItemClicked()
//            expectNoEvents()
//        }
//    }
//
//    private fun setThemeMode(darkThemeMode: DarkThemeMode) {
//        every { observeDarkThemeModeUseCaseMock.execute() } returns MutableStateFlow(darkThemeMode)
//    }
//
//    private fun createAndInitializeSUT() {
//        _SUT = SettingsViewModel(
//            observeDarkThemeModeUseCase = observeDarkThemeModeUseCaseMock,
//            setDarkThemeModeUseCase = setDarkThemeModeUseCaseMock
//        )
//    }


    @Test
    fun booleanFlowTest() = runTest {
        val booleanFlow = MutableStateFlow(false)
        booleanFlow.test {
            assertEquals(false, awaitItem())
            booleanFlow.value = true
//            assertEquals(true, awaitItem())
            expectNoEvents()
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