package com.example.bookstore.feature.review

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.bookstore.data.model.ReviewItem
import com.example.bookstore.viewmodel.ReviewViewModel

@Composable
fun ReviewScreen(
    nav: NavHostController,
    viewModel: ReviewViewModel,
    bookId: Int,
    userId: String
) {
    val reviews by viewModel.reviews.collectAsState()

    LaunchedEffect(true) {
        viewModel.loadReviews(bookId)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(reviews) { item ->

                ReviewCard(
                    item = item,
                    isOwner = item.userId == userId,
                    onEdit = {
                        nav.navigate("edit_review/${item.id}")
                    },
                    onDelete = {
                        viewModel.deleteReview(item.id!!, bookId)
                    }
                )

                Spacer(Modifier.height(12.dp))
            }
        }

        FloatingActionButton(
            onClick = { nav.navigate("add_review/$bookId") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text("+")
        }
    }
}

@Composable
fun ReviewCard(
    item: ReviewItem,
    isOwner: Boolean,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(12.dp)) {

            Text("Rating: ${item.rating}/5", style = MaterialTheme.typography.titleMedium)

            // UBAH INI
            Text(item.reviewText)

            item.imageUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )
            }

            if (isOwner) {
                Row {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, "Edit")
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, "Delete")
                    }
                }
            }
        }
    }
}
