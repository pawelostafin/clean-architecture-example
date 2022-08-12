package com.example.cleanarchitectureexample.ui.main

import androidx.navigation.fragment.NavHostFragment
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.databinding.ActivityMainBinding
import com.example.cleanarchitectureexample.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(ActivityMainBinding::inflate) {

    override val viewModel: MainViewModel by viewModel()

    override fun initView() {
        super.initView()

        initNavigation()
    }

    override fun initObservers() {
        super.initObservers()
    }

    private fun initNavigation() {
        val navHostFragment = binding.navHostContainer.getFragment() as NavHostFragment
        navHostFragment.navController.apply {
            val graph = navInflater.inflate(R.navigation.main_graph).apply {
                setStartDestination(R.id.testFragment)
            }
            setGraph(graph, null)
        }
    }

}