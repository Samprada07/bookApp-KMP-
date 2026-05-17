package com.example.bookapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.domain.model.Book
import com.example.bookapp.domain.usecase.DeleteBookUseCase
import com.example.bookapp.domain.usecase.GetBooksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class BookListState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class BookListViewModel(
    private val getBooksUseCase: GetBooksUseCase,
    private val deleteBookUseCase: DeleteBookUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BookListState())
    val state: StateFlow<BookListState> = _state

    init {
        getBooks()
    }

    fun getBooks() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            getBooksUseCase().fold(
                onSuccess = { books ->
                    _state.value = _state.value.copy(books = books, isLoading = false)
                },
                onFailure = { error ->
                    _state.value = _state.value.copy(error = error.message, isLoading = false)
                }
            )
        }
    }

    fun deleteBook(id: Int) {
        viewModelScope.launch {
            deleteBookUseCase(id).fold(
                onSuccess = { getBooks() },
                onFailure = { error ->
                    _state.value = _state.value.copy(error = error.message)
                }
            )
        }
    }
}