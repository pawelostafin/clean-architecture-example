package com.example.data.di

import com.example.data.network.di.networkModule
import com.example.data.repository.di.repositoryModule

val dataModules = listOf(
    repositoryModule,
    networkModule
)