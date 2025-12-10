package com.example.bookstore.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WishlistItem(
    val id: Int? = null,
    val user_id: String,
    val title: String,
    val author: String,
    val imageUrl: String = ""
)
