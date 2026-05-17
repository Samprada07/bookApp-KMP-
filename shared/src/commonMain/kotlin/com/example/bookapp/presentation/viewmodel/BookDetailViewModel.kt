package com.example.bookapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.domain.model.Book
import com.example.bookapp.domain.usecase.GetBookByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class BookDetailState(
    val book: Book? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class BookDetailViewModel(
    private val getBookByIdUseCase: GetBookByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BookDetailState())
    val state: StateFlow<BookDetailState> = _state

    fun getBook(id: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            getBookByIdUseCase(id).fold(
                onSuccess = { book ->
                    _state.value = _state.value.copy(book = book, isLoading = false)
                },
                onFailure = { error ->
                    _state.value = _state.value.copy(error = error.message, isLoading = false)
                }
            )
        }
    }
}