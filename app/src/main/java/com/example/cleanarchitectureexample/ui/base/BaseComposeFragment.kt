package com.example.cleanarchitectureexample.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.ui.utli.dialog.ComposeProgressDialog
import com.example.cleanarchitectureexample.ui.utli.dialog.ErrorDialog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class BaseComposeFragment<ViewModel : BaseViewModel> : Fragment() {

    protected abstract val viewModel: ViewModel

    private val requireBaseActivity: BaseActivity<*, *>
        get() = (requireActivity() as? BaseActivity<*, *>) ?: error("BaseActivity is required")

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_base_comopose, container, false)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnBackPressedDispatcher()

        val composeView = view.findViewById<ComposeView>(R.id.composeView)
        composeView?.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner))
            setContent {
                val progressDialogVisibility by viewModel.progressDialogVisibility.collectAsState()
                val errorDialogState by viewModel.errorDialogState.collectAsState()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ContentView()
                    ProgressDialogHandler(isVisible = progressDialogVisibility)
                    ErrorDialogHandler(state = errorDialogState)
                }
            }
        }
        initObservers()
        viewModel.initializeIfNeeded()
    }

    protected abstract fun onBackPressed()

    @Composable
    protected abstract fun ContentView()

    @CallSuper
    protected open fun initObservers() = Unit

    private fun setupOnBackPressedDispatcher() {
        interceptActivityOnBackPressed { onBackPressed() }
    }

    @Composable
    private fun ProgressDialogHandler(isVisible: Boolean) {
        if (isVisible) {
            ComposeProgressDialog()
        }
    }

    @Composable
    private fun ErrorDialogHandler(state: ErrorDialogState) {
        when (state) {
            ErrorDialogState.Hidden -> Unit
            is ErrorDialogState.Visible -> {
                ErrorDialog(
                    message = state.throwable.toErrorMessage(),
                    onDismissRequest = viewModel::errorDialogDismissRequested
                )
            }
        }
    }

    @Composable
    private fun Throwable.toErrorMessage(): String {
        val default = stringResource(R.string.error_message_unexpected)
        return message ?: default
    }

}

fun Fragment.interceptActivityOnBackPressed(handleOnBackPressed: OnBackPressedCallback.() -> Unit) {
    val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            handleOnBackPressed.invoke(this)
        }
    }
    requireActivity()
        .onBackPressedDispatcher
        .addCallback(viewLifecycleOwner, onBackPressedCallback)
}

fun <T> BaseActivity<*, *>.observe(flow: Flow<T>, action: (T) -> Unit) {
    flow
        .onEach { action.invoke(it) }
        .flowWithLifecycle(lifecycle)
        .launchIn(lifecycleScope)
}

fun <T> Fragment.observe(flow: Flow<T>, action: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED) {
            flow.collect { action.invoke(it) }
        }
    }
}