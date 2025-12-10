package com.example.bookstore.data

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.user.UserSession
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object SupabaseManager {

    const val SUPABASE_URL = "https://corjebstbpsotdtwpxky.supabase.co"
    const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNvcmplYnN0YnBzb3RkdHdweGt5Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjQ1NzczNzIsImV4cCI6MjA4MDE1MzM3Mn0._BcGcfZxNX0f2oBh2Evq4xpAIHRwoXDYWTd7QuxvvQM"

    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Auth)
        install(Postgrest.Companion)
        install(Storage.Companion)
    }

    fun session(): UserSession? = client.auth.currentSessionOrNull()

}
