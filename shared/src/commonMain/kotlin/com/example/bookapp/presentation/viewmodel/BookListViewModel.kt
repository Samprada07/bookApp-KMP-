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
    val filteredBooks: List<Book> = emptyList(),
    val searchQuery: String = "",
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
                    _state.value = _state.value.copy(
                        books = books,
                        filteredBooks = books,
                        isLoading = false
                    )
                },
                onFailure = { error ->
                    _state.value = _state.value.copy(error = error.message, isLoading = false)
                }
            )
        }
    }

    fun search(query: String) {
        _state.value = _state.value.copy(
            searchQuery = query,
            filteredBooks = if (query.isEmpty()) _state.value.books
            else _state.value.books.filter {
                it.title.contains(query, ignoreCase = true)
            }
        )
    }

    fun deleteBook(id: Int) {
        viewModelScope.launch {
            deleteBookUseCase(id).fold(
                onSuccess = {
                    val updatedBooks = _state.value.books.filter { it.id != id }
                    _state.value = _state.value.copy(
                        books = updatedBooks,
                        filteredBooks = updatedBooks.filter {
                            it.title.contains(_state.value.searchQuery, ignoreCase = true)
                        }
                    )
                },
                onFailure = { error ->
                    _state.value = _state.value.copy(error = error.message)
                }
            )
        }
    }
}