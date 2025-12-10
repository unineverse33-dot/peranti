package com.example.bookstore.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Int? = null,
    val user_id: String,
    val content: String,
    val image_url: String? = null,
    val created_at: String? = null,
    val likes: Int = 0
)
