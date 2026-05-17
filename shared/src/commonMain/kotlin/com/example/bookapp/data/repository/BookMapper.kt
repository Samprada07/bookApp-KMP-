package com.example.bookapp.data.repository

import com.example.bookapp.data.remote.dto.BookDto
import com.example.bookapp.domain.model.Book

fun BookDto.toDomain() = Book(
    id = id,
    title = title,
    description = description,
    pageCount = pageCount,
    excerpt = excerpt,
    publishDate = publishDate
)

fun Book.toDto() = BookDto(
    id = id,
    title = title,
    description = description,
    pageCount = pageCount,
    excerpt = excerpt,
    publishDate = publishDate
)