package com.example.bookstore.data.repository

import com.example.bookstore.data.SupabaseManager
import com.example.bookstore.data.model.BookItem
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage

class BookRepository {

    private val client = SupabaseManager.client

    // GET ALL BOOKS
    suspend fun getBooks(): List<BookItem> {
        return client.postgrest["books"]
            .select()
            .decodeList()
    }

    // UPLOAD IMAGE
    suspend fun uploadImage(bytes: ByteArray): String {
        val bucket = client.storage["book_images"]
        val path = "img_${System.currentTimeMillis()}.jpg"

        bucket.upload(path, bytes)
        return bucket.publicUrl(path)
    }

    // CREATE
    suspend fun addBook(item: BookItem) {
        client.postgrest["books"]
            .insert(item)
    }

    // UPDATE FIX — Supabase Kotlin v2+
    suspend fun updateBook(id: Int, item: BookItem) {
        client.postgrest["books"]
            .update(
                {
                    BookItem::title setTo item.title
                    BookItem::author setTo item.author
                    BookItem::imageUrl setTo item.imageUrl
                }
            ) {
                filter {
                    BookItem::id eq id
                }
            }
    }

    // DELETE FIX — Supabase Kotlin v2+
    suspend fun deleteBook(id: Int) {
        client.postgrest["books"]
            .delete {
                filter {
                    BookItem::id eq id
                }
            }
    }
}
