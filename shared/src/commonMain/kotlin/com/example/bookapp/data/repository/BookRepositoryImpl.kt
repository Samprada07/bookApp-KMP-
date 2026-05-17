package com.example.bookapp.data.repository

import com.example.bookapp.data.remote.BookApiService
import com.example.bookapp.domain.model.Book
import com.example.bookapp.domain.repository.BookRepository


class BookRepositoryImpl(
    private val apiService: BookApiService
) : BookRepository {

    override suspend fun getBooks(): Result<List<Book>> = runCatching {
        apiService.getBooks().map { it.toDomain() }
    }

    override suspend fun getBookById(id: Int): Result<Book> = runCatching {
        apiService.getBookById(id).toDomain()
    }

    override suspend fun addBook(book: Book): Result<Book> = runCatching {
        apiService.addBook(book.toDto()).toDomain()
    }

    override suspend fun deleteBook(id: Int): Result<Unit> = runCatching {
        apiService.deleteBook(id)
    }
}