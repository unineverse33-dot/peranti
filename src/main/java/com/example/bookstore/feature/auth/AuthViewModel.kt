package com.example.bookstore.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstore.data.SupabaseManager
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // LOGIN
    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _loading.value = true
                SupabaseManager.client.auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }
                onSuccess()
            } catch (e: HttpRequestException) {
                _error.value = "Email atau password salah!"
            } finally {
                _loading.value = false
            }
        }
    }

    // REGISTER
    fun register(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _loading.value = true
                SupabaseManager.client.auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                }
                onSuccess()
            } catch (e: Exception) {
                _error.value = "Registrasi gagal: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
