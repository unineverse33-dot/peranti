package com.example.bookstore.data.repository

import com.example.bookstore.data.model.Post
import com.example.bookstore.data.SupabaseManager
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class PostRepository {

    private val supabase = SupabaseManager.client
    private val storage = supabase.storage.from("community")

    suspend fun uploadImage(bytes: ByteArray): String {
        val filename = "post_${UUID.randomUUID()}.jpg"

        storage.upload(filename, bytes)
        return storage.publicUrl(filename)
    }

    suspend fun createPost(post: Post) {
        supabase.from("posts").insert(post)
    }

    suspend fun getAllPosts(): List<Post> =
        supabase.from("posts").select().decodeList<Post>()

    suspend fun updatePost(id: Int, content: String) {
        supabase.from("posts").update(
            { Post::content setTo content }
        ) {
            filter { Post::id eq id }
        }
    }

    suspend fun deletePost(id: Int) {
        supabase.from("posts").delete {
            filter { Post::id eq id }
        }
    }

    suspend fun likePost(id: Int, newLikes: Int) {
        supabase.from("posts").update(
            { Post::likes setTo newLikes }
        ) {
            filter { Post::id eq id }
        }
    }
}
