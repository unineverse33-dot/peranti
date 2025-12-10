package com.example.bookstore.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookstore.feature.home.HomeScreen
import com.example.bookstore.feature.review.ReviewScreen
import com.example.bookstore.feature.wishlist.WishlistScreen
import com.example.bookstore.feature.katalog.KatalogScreen
import com.example.bookstore.feature.history.HistoryScreen
import com.example.bookstore.feature.community.CommunityScreen
import com.example.bookstore.viewmodel.KatalogViewModel
import com.example.bookstore.viewmodel.ReviewViewModel
import com.example.bookstore.viewmodel.WishlistViewModel
import com.example.bookstore.viewmodel.HistoryViewModel
import com.example.bookstore.viewmodel.CommunityViewModel

@Composable
fun AppNavigation() {
    val nav = rememberNavController()
    val userId = "user123" // nanti diganti auth Supabase

    NavHost(
        navController = nav,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(nav)
        }

        // REVIEW WITH PARAMS
        composable("review/{bookId}") { backStack ->
            val bookId = backStack.arguments?.getString("bookId")?.toInt() ?: 0
            val vm: ReviewViewModel = viewModel()

            ReviewScreen(
                nav = nav,
                viewModel = vm,
                bookId = bookId,
                userId = userId
            )
        }

        // WISHLIST
        composable("wishlist") {
            val vm: WishlistViewModel = viewModel()

            WishlistScreen(
                nav = nav,
                viewModel = vm,
                userId = userId
            )
        }

        // KATALOG
        composable("katalog") {
            val vm: KatalogViewModel = viewModel()
            KatalogScreen(viewModel = vm)
        }

        // HISTORY
        composable("history") {
            val vm: HistoryViewModel = viewModel()

            HistoryScreen(
                userId = userId,
                viewModel = vm
            )
        }

        // COMMUNITY
        composable("community") {
            val vm: CommunityViewModel = viewModel()

            CommunityScreen(
                nav = nav,
                viewModel = vm,
                userId = userId
            )
        }
    }
}
