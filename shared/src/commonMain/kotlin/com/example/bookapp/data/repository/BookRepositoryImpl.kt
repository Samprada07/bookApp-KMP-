package com.example.bookapp.data.repository

import com.example.bookapp.data.local.BookLocalDataSource
import com.example.bookapp.domain.repository.BookRepository
import com.example.bookapp.data.remote.BookApiService
import com.example.bookapp.domain.model.Book

class BookRepositoryImpl(
    private val apiService: BookApiService,
    private val localDataSource: BookLocalDataSource
) : BookRepository {

    override suspend fun getBooks(): Result<List<Book>> = runCatching {
        val remote = apiService.getBooks().map { it.toDomain() }
        remote.forEach { localDataSource.insertBook(it) }
        remote
    }.recoverCatching {
        val cached = localDataSource.getAllBooks()
        if (cached.isEmpty()) throw it
        cached
    }

    override suspend fun getBookById(id: Int): Result<Book> = runCatching {
        apiService.getBookById(id).toDomain()
    }

    override suspend fun addBook(book: Book): Result<Book> = runCatching {
        val added = apiService.addBook(book.toDto()).toDomain()
        localDataSource.insertBook(added)
        added
    }

    override suspend fun deleteBook(id: Int): Result<Unit> = runCatching {
        apiService.deleteBook(id)
        localDataSource.deleteBook(id)
    }
}