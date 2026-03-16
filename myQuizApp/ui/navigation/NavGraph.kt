package com.goble.myquizapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.goble.myquizapp.data.Category
import com.goble.myquizapp.ui.home.HomeScreen
import com.goble.myquizapp.ui.question.QuestionScreen
import com.goble.myquizapp.ui.question.QuizViewModel
import com.goble.myquizapp.ui.results.ResultsScreen

@Composable
fun QuizNavGraph(
    navController: NavHostController = rememberNavController()
) {
    val quizViewModel: QuizViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // ----- Home ------------------------------------------------
        composable(Screen.Home.route) {
            HomeScreen(
                onStartQuiz = { category ->
                    navController.navigate(Screen.Question.createRoute(category))
                }
            )
        }
        // ----- Question --------------------------------------------
        composable(
            route = Screen.Question.route,
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: return@composable
            val category = Category.valueOf(categoryName)

            QuestionScreen(
                category = category,
                viewModel = quizViewModel,
                onQuizFinished = {
                    // KEY: pop all question screens off the stack so
                    // pressing Back from ResultsScreen goes straight Home
                    navController.navigate(Screen.Results.route) {
                        popUpTo(Screen.Home.route) {
                            inclusive = false
                        }
                    }
                }
            )
        }
        // ----- Result ----------------------------------------------
        composable(Screen.Results.route) {
            ResultsScreen(
                viewModel = quizViewModel,
                onPlayAgain = {
                    // Simply pop Results; Home is now on top of the stack
                    navController.popBackStack()
                }
            )
        }
    }
}