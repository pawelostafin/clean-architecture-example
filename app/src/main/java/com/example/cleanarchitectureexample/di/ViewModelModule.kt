package com.example.cleanarchitectureexample.di

import com.example.cleanarchitectureexample.ui.main.MainViewModel
import com.example.cleanarchitectureexample.ui.test.TestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        TestViewModel()
    }

    viewModel {
        MainViewModel()
    }

}