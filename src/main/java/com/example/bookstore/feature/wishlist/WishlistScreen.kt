@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.bookstore.feature.wishlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.bookstore.data.model.WishlistItem
import com.example.bookstore.viewmodel.WishlistViewModel

@Composable
fun WishlistScreen(
    nav: NavHostController,       // <-- WAJIB ADA
    viewModel: WishlistViewModel,
    userId: String
) {
    val wishlist by viewModel.wishlist.collectAsState()

    LaunchedEffect(true) {
        viewModel.loadWishlist(userId)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        TopAppBar(
            title = { Text("Wishlist Buku") }
        )

        Spacer(Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {

            items(wishlist) { item ->
                WishlistCard(
                    item = item,
                    onRemove = { viewModel.removeItem(item.id) }
                )
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun WishlistCard(
    item: WishlistItem,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = item.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.LightGray)
            )

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(item.title, style = MaterialTheme.typography.titleMedium)
                Text(item.author, color = Color.Gray)
            }

            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete, contentDescription = "Hapus")
            }
        }
    }
}
