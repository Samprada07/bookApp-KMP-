package com.example.bookapp.domain.usecase

import com.example.bookapp.domain.repository.BookRepository

class DeleteBookUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(id: Int): Result<Unit> = repository.deleteBook(id)
}