package com.example.bookstore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstore.data.model.ReviewItem
import com.example.bookstore.data.repository.ReviewRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReviewViewModel : ViewModel() {

    private val repo = ReviewRepository()

    private val _reviews = MutableStateFlow<List<ReviewItem>>(emptyList())
    val reviews: StateFlow<List<ReviewItem>> = _reviews

    fun loadReviews(bookId: Int) {
        viewModelScope.launch {
            _reviews.value = repo.getReviews(bookId)
        }
    }

    fun addReview(
        bookId: Int,
        userId: String,
        rating: Int,
        comment: String,
        imageBytes: ByteArray?
    ) {
        viewModelScope.launch {
            val imageUrl =
                if (imageBytes != null) repo.uploadImage(imageBytes) else null

            val newReview = ReviewItem(
                bookId = bookId,
                userId = userId,
                rating = rating,
                reviewText = comment,
                title = null,
                imageUrl = imageUrl
            )

            repo.addReview(newReview)
            loadReviews(bookId)
        }
    }

    fun updateReview(id: Int, bookId: Int, item: ReviewItem) {
        viewModelScope.launch {
            repo.updateReview(id, item)
            loadReviews(bookId)
        }
    }

    fun deleteReview(id: Int, bookId: Int) {
        viewModelScope.launch {
            repo.deleteReview(id)
            loadReviews(bookId)
        }
    }
}
