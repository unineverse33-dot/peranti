package com.example.bookstore.data.model

data class ReviewItem(
    val id: Int? = null,
    val bookId: Int,
    val userId: String,
    val rating: Int,
    val reviewText: String,
    val title: String? = null,
    val imageUrl: String? = null
)
