package com.example.bookstore.data.repository

import com.example.bookstore.data.SupabaseManager
import com.example.bookstore.data.model.WishlistItem
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage

class WishlistRepository {


    private val client = SupabaseManager.client

    // GET wishlist by user
    suspend fun getWishlist(userId: String): List<WishlistItem> {
        return client.postgrest["wishlist"]
            .select {
                filter { eq("user_id", userId) }
            }
            .decodeList()
    }

    // UPLOAD IMAGE
    suspend fun uploadBookImage(bytes: ByteArray): String {
        val bucket = client.storage["book_images"]
        val path = "img_${System.currentTimeMillis()}.jpg"

        bucket.upload(path, bytes)
        return bucket.publicUrl(path)
    }

    // INSERT wishlist
    suspend fun addToWishlist(item: WishlistItem) {
        client.postgrest["wishlist"].insert(item)
    }

    // DELETE wishlist
    suspend fun deleteWishlist(id: Int) {
        client.postgrest["wishlist"].delete {
            filter { eq("id", id) }
        }
    }
}
