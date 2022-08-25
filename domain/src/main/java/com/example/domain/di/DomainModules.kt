package com.example.domain.di

import com.example.domain.provider.di.providerModule
import com.example.domain.usecase.di.useCaseModule

val domainModules = listOf(
    useCaseModule,
    providerModule
)