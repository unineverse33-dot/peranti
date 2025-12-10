@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.bookstore.feature.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bookstore.viewmodel.HistoryViewModel

@Composable
fun HistoryScreen(
    userId: String,
    viewModel: HistoryViewModel
) {
    val history by viewModel.history.collectAsState()

    LaunchedEffect(true) {
        viewModel.loadHistory(userId)
    }

    Column(Modifier.fillMaxSize()) {

        TopAppBar(
            title = { Text("Riwayat Pembelian") }
        )

        LazyColumn(Modifier.padding(16.dp)) {
            items(history) { item ->
                HistoryCard(item)
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun HistoryCard(item: com.example.bookstore.data.model.HistoryItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = item.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .background(Color.LightGray)
            )

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(item.bookTitle, style = MaterialTheme.typography.titleMedium)
                Text("Tanggal: ${item.date}", color = Color.Gray)
                Text("Harga: Rp ${item.price}", color = Color.Gray)
            }

            Text(
                text = item.status,
                color = when (item.status) {
                    "Completed" -> Color(0xFF4CAF50)
                    "Pending" -> Color(0xFFFFC107)
                    else -> Color(0xFFF44336)
                }
            )
        }
    }
}
