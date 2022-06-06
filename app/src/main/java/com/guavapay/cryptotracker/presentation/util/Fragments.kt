package com.guavapay.cryptotracker.presentation.util

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun Fragment.safeNavigate(
    directions: NavDirections) = try {
    val navController = findNavController()
        navController.navigate(directions)
} catch (e: Exception) {
    e.printStackTrace()
}