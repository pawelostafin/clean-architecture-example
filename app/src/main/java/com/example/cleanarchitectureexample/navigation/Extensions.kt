package com.example.cleanarchitectureexample.navigation

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController

fun Fragment.navigateBack() {
    val navHostFragment = requireParentFragment() as NavHostFragment
    val navController = findNavController()
    if (navHostFragment.childFragmentManager.backStackEntryCount == 0) {
        requireActivity().finish()
    } else {
        navController.popBackStack()
    }
}