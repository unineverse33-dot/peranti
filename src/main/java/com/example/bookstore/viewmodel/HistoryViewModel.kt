package com.example.bookstore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstore.data.model.HistoryItem
import com.example.bookstore.data.repository.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {

    private val repo = HistoryRepository()

    private val _history = MutableStateFlow<List<HistoryItem>>(emptyList())
    val history: StateFlow<List<HistoryItem>> = _history

    fun loadHistory(userId: String) {
        viewModelScope.launch {
            try {
                _history.value = repo.getHistory(userId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
