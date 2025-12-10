package com.example.bookstore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstore.data.model.WishlistItem
import com.example.bookstore.data.repository.WishlistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WishlistViewModel : ViewModel() {

    private val repo = WishlistRepository()

    private val _wishlist = MutableStateFlow<List<WishlistItem>>(emptyList())
    val wishlist: StateFlow<List<WishlistItem>> = _wishlist

    private val _loading = MutableStateFlow(false)
    val loading = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error = _error

    // LOAD DATA
    fun loadWishlist(userId: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _wishlist.value = repo.getWishlist(userId)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    // ADD BOOK
    fun addBook(userId: String, title: String, author: String, imageBytes: ByteArray?) {
        viewModelScope.launch {
            try {
                _loading.value = true

                val imageUrl = if (imageBytes != null) {
                    repo.uploadBookImage(imageBytes)
                } else {
                    ""
                }

                val item = WishlistItem(
                    id = null,
                    user_id = userId,
                    title = title,
                    author = author,
                    imageUrl = imageUrl
                )

                repo.addToWishlist(item)
                loadWishlist(userId)

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    // DELETE (kompatibel dengan screen)
    fun removeItem(id: Int?) {
        if (id == null) return
        viewModelScope.launch {
            repo.deleteWishlist(id)
            _wishlist.value = _wishlist.value.filterNot { it.id == id }
        }
    }

    // DELETE dengan reload (kalau butuh)
    fun deleteBook(id: Int, userId: String) {
        viewModelScope.launch {
            repo.deleteWishlist(id)
            loadWishlist(userId)
        }
    }
}
