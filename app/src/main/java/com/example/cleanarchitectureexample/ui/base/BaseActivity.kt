package com.example.cleanarchitectureexample.ui.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<ViewModel : BaseViewModel, B : ViewBinding>(
    private val bindingInflater: ActivityBindingInflater<B>,
) : AppCompatActivity() {

    private var _binding: B? = null
    protected val binding: B get() = requireNotNull(_binding)
    abstract val viewModel: ViewModel

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)

        initView()
        initObservers()
    }

    @CallSuper
    protected open fun initView() = Unit

    @CallSuper
    protected open fun initObservers() = Unit

    @CallSuper
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}