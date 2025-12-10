package com.example.bookstore

import android.app.Application
import com.example.bookstore.data.SupabaseManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SupabaseManager.init(this)
    }
}
