package com.example.cleanarchitectureexample.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup

typealias ActivityBindingInflater<Binding> =
            (layoutInflater: LayoutInflater) -> Binding

typealias FragmentBindingInflater<Binding> =
            (layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> Binding