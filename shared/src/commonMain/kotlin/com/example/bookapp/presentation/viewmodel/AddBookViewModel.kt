package com.example.bookapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.domain.model.Book
import com.example.bookapp.domain.usecase.AddBookUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AddBookState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

class AddBookViewModel(
    private val addBookUseCase: AddBookUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddBookState())
    val state: StateFlow<AddBookState> = _state

    fun addBook(title: String, description: String, pageCount: Int, excerpt: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val book = Book(
                id = 0,
                title = title,
                description = description,
                pageCount = pageCount,
                excerpt = excerpt,
                publishDate = "2026-01-01T00:00:00Z"
            )
            addBookUseCase(book).fold(
                onSuccess = {
                    _state.value = _state.value.copy(isLoading = false, isSuccess = true)
                },
                onFailure = { error ->
                    _state.value = _state.value.copy(isLoading = false, error = error.message)
                }
            )
        }
    }
}