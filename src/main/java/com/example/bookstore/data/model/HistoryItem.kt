package com.example.bookstore.data.model


import kotlinx.serialization.Serializable

@Serializable
data class HistoryItem(
    val id: Int? = null,
    val userId: String,
    val bookTitle: String,
    val imageUrl: String?,
    val price: Double,
    val date: String,
    val status: String // "Completed", "Pending", "Failed"
)
