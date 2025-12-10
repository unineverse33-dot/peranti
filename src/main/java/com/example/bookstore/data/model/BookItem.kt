package com.example.bookstore.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BookItem(
    val id: Int? = null,
    val title: String,
    val author: String,
    val imageUrl: String? = null
)
