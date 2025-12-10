package com.example.bookstore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstore.data.model.BookItem
import com.example.bookstore.data.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class KatalogViewModel : ViewModel() {

    private val repo = BookRepository()

    private val _books = MutableStateFlow<List<BookItem>>(emptyList())
    val books: StateFlow<List<BookItem>> = _books

    private val _loading = MutableStateFlow(false)
    val loading = _loading

    fun loadBooks() {
        viewModelScope.launch {
            _books.value = repo.getBooks()
        }
    }

    fun addBook(title: String, author: String, bytes: ByteArray?) {
        viewModelScope.launch {

            val imageUrl = if (bytes != null) repo.uploadImage(bytes) else ""

            val item = BookItem(
                title = title,
                author = author,
                imageUrl = imageUrl
            )

            repo.addBook(item)
            loadBooks()
        }
    }

    fun editBook(id: Int, title: String, author: String, bytes: ByteArray?) {
        viewModelScope.launch {

            val imageUrl = if (bytes != null) repo.uploadImage(bytes) else null

            val updated = BookItem(
                id = id,
                title = title,
                author = author,
                imageUrl = imageUrl
            )

            repo.updateBook(id, updated)
            loadBooks()
        }
    }

    fun deleteBook(id: Int) {
        viewModelScope.launch {
            repo.deleteBook(id)
            loadBooks()
        }
    }
}
