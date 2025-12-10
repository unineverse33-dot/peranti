package com.example.bookstore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstore.data.model.Post
import com.example.bookstore.data.repository.PostRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CommunityViewModel : ViewModel() {

    private val repo = PostRepository()

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun loadPosts() {
        viewModelScope.launch {
            try {
                _loading.value = true
                _posts.value = repo.getAllPosts()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun createPost(userId: String, text: String, imageBytes: ByteArray?) {
        viewModelScope.launch {
            try {
                _loading.value = true

                val imageUrl = if (imageBytes != null) {
                    repo.uploadImage(imageBytes)
                } else null

                val newPost = Post(
                    user_id = userId,
                    content = text,
                    image_url = imageUrl
                )

                repo.createPost(newPost)
                loadPosts()

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun likePost(post: Post) {
        viewModelScope.launch {
            repo.likePost(post.id!!, post.likes + 1)
            loadPosts()
        }
    }

    fun deletePost(id: Int) {
        viewModelScope.launch {
            repo.deletePost(id)
            loadPosts()
        }
    }
}
