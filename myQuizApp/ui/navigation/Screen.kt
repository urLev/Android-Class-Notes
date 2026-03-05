package com.goble.myquizapp.ui.navigation

import com.goble.myquizapp.data.Category

sealed class Screen(val route:String) {
    // Home -- no arguments needed
    object Home:Screen("home")
    //Question -- Receives the category name as a path argument
    object Question:Screen("question/{categoryName}") {
        fun createRoute(category: Category) = "question/${category.name}"
    }
    //Results -- no arguments; reads state directly from shared viewModel
    object Results:Screen("results")
}