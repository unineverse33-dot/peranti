package com.example.bookstore.data.repository

import com.example.bookstore.data.SupabaseManager
import com.example.bookstore.data.model.HistoryItem
import io.github.jan.supabase.postgrest.postgrest

class HistoryRepository {

    private val client = SupabaseManager.client

    // GET history by user (pakai query string)
    suspend fun getHistory(userId: String): List<HistoryItem> {
        return client.postgrest["purchase_history?id=eq.$userId"]
            .select()
            .decodeList<HistoryItem>()
    }

    // INSERT new purchase history
    suspend fun addHistory(item: HistoryItem) {
        client.postgrest["purchase_history"]
            .insert(item)
    }

    // UPDATE data (pakai query string untuk kondisi)
    suspend fun updateHistory(id: String, updatedData: Map<String, Any?>): Boolean {
        client.postgrest["purchase_history?id=eq.$id"]
            .update(updatedData)
        return true
    }

    // DELETE data (pakai query string untuk kondisi)
    suspend fun deleteHistory(id: String): Boolean {
        client.postgrest["purchase_history?id=eq.$id"]
            .delete()
        return true
    }
}
