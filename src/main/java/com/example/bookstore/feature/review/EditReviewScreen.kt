package com.example.bookstore.feature.review

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bookstore.data.model.ReviewItem
import com.example.bookstore.data.repository.ReviewRepository
import kotlinx.coroutines.launch

@Composable
fun EditReviewScreen(
    nav: NavController,
    reviewId: Int,
    repository: ReviewRepository = ReviewRepository()
) {
    val scope = rememberCoroutineScope()

    var review by remember { mutableStateOf<ReviewItem?>(null) }
    var loading by remember { mutableStateOf(true) }

    // MUAT DATA REVIEW BERDASARKAN ID
    LaunchedEffect(reviewId) {
        val data = repository.getReviewById(reviewId)
        review = data
        loading = false
    }

    if (loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val current = review ?: return

    // ✅ FIX: title.orEmpty() biar tidak nullable
    var title by remember { mutableStateOf(current.title.orEmpty()) }
    var reviewText by remember { mutableStateOf(current.reviewText) }
    var rating by remember { mutableStateOf(current.rating.toString()) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Text("Edit Review", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(20.dp))

        // INPUT JUDUL
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Review Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(15.dp))

        // INPUT REVIEW
        OutlinedTextField(
            value = reviewText,
            onValueChange = { reviewText = it },
            label = { Text("Your Review") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        Spacer(Modifier.height(15.dp))

        // INPUT RATING
        OutlinedTextField(
            value = rating,
            onValueChange = { rating = it },
            label = { Text("Rating (1–5)") },
            modifier = Modifier.fillMaxWidth()
        )

        if (errorMessage.isNotEmpty()) {
            Text(
                errorMessage,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(Modifier.height(25.dp))

        Button(
            onClick = {
                if (title.isBlank() || reviewText.isBlank() || rating.isBlank()) {
                    errorMessage = "Semua field wajib diisi!"
                    return@Button
                }

                val rate = rating.toIntOrNull()
                if (rate == null || rate !in 1..5) {
                    errorMessage = "Rating harus angka 1–5"
                    return@Button
                }

                val updated = ReviewItem(
                    id = reviewId,
                    title = title,               // sudah non-null
                    reviewText = reviewText,
                    rating = rate,
                    userId = current.userId,
                    bookId = current.bookId,
                    imageUrl = current.imageUrl // biar tidak hilang!
                )

                scope.launch {
                    repository.updateReview(reviewId, updated)
                    nav.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Changes")
        }

        Spacer(Modifier.height(10.dp))

        OutlinedButton(
            onClick = { nav.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}
