package com.example.bookstore.feature.katalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bookstore.data.model.BookItem
import com.example.bookstore.viewmodel.KatalogViewModel

@Composable
fun KatalogScreen(viewModel: KatalogViewModel) {
    val books by viewModel.books.collectAsState()

    LaunchedEffect(true) {
        viewModel.loadBooks()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.addBook("Judul Baru", "Author Baru", null)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Buku")
            }
        }
    ) { padding ->

        LazyColumn(modifier = Modifier.padding(padding)) {
            items(books) { item ->
                BookCard(
                    item = item,
                    onDelete = { viewModel.deleteBook(item.id!!) },
                    onEdit = { viewModel.editBook(item.id!!, "Edited", "Edited", null) }
                )
            }
        }
    }
}

@Composable
fun BookCard(
    item: BookItem,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.imageUrl,
                modifier = Modifier.size(80.dp),
                contentDescription = null
            )

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(item.title, style = MaterialTheme.typography.titleMedium)
                Text(item.author, color = MaterialTheme.colorScheme.secondary)
            }

            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Buku")
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Hapus Buku")
            }
        }
    }
}
