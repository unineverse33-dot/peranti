package com.example.bookstore.data.repository

import com.example.bookstore.data.SupabaseManager
import com.example.bookstore.data.model.ReviewItem
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage

class ReviewRepository {

    private val client = SupabaseManager.client

    // GET reviews by bookId
    suspend fun getReviews(bookId: Int): List<ReviewItem> {
        return client.postgrest["reviews?bookId=eq.$bookId"]
            .select()
            .decodeList()
    }

    // GET review by ID
    suspend fun getReviewById(id: Int): ReviewItem? {
        return client.postgrest["reviews?id=eq.$id"]
            .select()
            .decodeList<ReviewItem>()
            .firstOrNull()
    }

    // UPLOAD image
    suspend fun uploadImage(bytes: ByteArray): String {
        val bucket = client.storage["review_images"]
        val path = "review_${System.currentTimeMillis()}.jpg"
        bucket.upload(path, bytes, upsert = true)
        return bucket.publicUrl(path)
    }

    // CREATE
    suspend fun addReview(item: ReviewItem) {
        client.postgrest["reviews"].insert(item)
    }

    // UPDATE
    suspend fun updateReview(id: Int, item: ReviewItem) {
        client.postgrest["reviews?id=eq.$id"].update(item)
    }

    // DELETE
    suspend fun deleteReview(id: Int) {
        client.postgrest["reviews?id=eq.$id"].delete()
    }
}
