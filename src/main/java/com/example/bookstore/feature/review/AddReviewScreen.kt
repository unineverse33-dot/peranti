package com.example.bookstore.feature.review

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bookstore.viewmodel.ReviewViewModel


@Composable
fun AddReviewScreen(
    nav: NavHostController,
    viewModel: ReviewViewModel,
    bookId: Int,
    userId: String
) {
    var comment by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(3) }
    var imageBytes by remember { mutableStateOf<ByteArray?>(null) } // isi via picker

    Column(Modifier.padding(16.dp)) {

        Text("Tambah Review", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            label = { Text("Komentar") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = rating.toString(),
            onValueChange = { rating = it.toIntOrNull() ?: 1 },
            label = { Text("Rating 1-5") }
        )

        Button(
            onClick = {
                viewModel.addReview(bookId, userId, rating, comment, imageBytes)
                nav.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}
