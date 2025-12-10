package com.example.bookstore.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            "Home",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        // REVIEW perlu bookId !
        HomeButton("📚 Review & Rating Buku") {
            navController.navigate("review/1")   // <-- FIX
        }

        HomeButton("❤️ Wishlist Buku") {
            navController.navigate("wishlist")
        }

        HomeButton("📦 CRUD Katalog Admin") {
            navController.navigate("katalog")
        }

        HomeButton("🛒 History Pembelian") {
            navController.navigate("history")
        }

        HomeButton("🌐 Community Sharing") {
            navController.navigate("community")
        }
    }
}

@Composable
fun HomeButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(55.dp)
    ) {
        Text(text)
    }
}
