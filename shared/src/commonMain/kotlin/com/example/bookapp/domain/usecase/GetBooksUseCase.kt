package com.example.bookapp.domain.usecase

import com.example.bookapp.domain.model.Book
import com.example.bookapp.domain.repository.BookRepository

class GetBooksUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(): Result<List<Book>> = repository.getBooks()
}