package com.example.bookapp.domain.usecase

import com.example.bookapp.domain.model.Book
import com.example.bookapp.domain.repository.BookRepository

class GetBookByIdUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(id: Int): Result<Book> = repository.getBookById(id)
}