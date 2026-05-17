package com.example.bookapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bookapp.presentation.screens.AddBookScreen
import com.example.bookapp.presentation.screens.BookDetailScreen
import com.example.bookapp.presentation.screens.BookListScreen
import com.example.bookapp.presentation.screens.SplashScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object BookList : Screen("book_list")
    object AddBook : Screen("add_book")
    object BookDetail : Screen("book_detail/{bookId}") {
        fun createRoute(bookId: Int) = "book_detail/$bookId"
    }
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onNavigate = {
                navController.navigate(Screen.BookList.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(Screen.BookList.route) {
            BookListScreen(
                onBookClick = { bookId ->
                    navController.navigate(Screen.BookDetail.createRoute(bookId))
                },
                onAddClick = {
                    navController.navigate(Screen.AddBook.route)
                }
            )
        }
        composable(Screen.AddBook.route) {
            AddBookScreen(onBack = { navController.popBackStack() })
        }
        composable(
            route = Screen.BookDetail.route,
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: return@composable
            BookDetailScreen(
                bookId = bookId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}