package com.example.bookapp.data.local

import com.example.bookapp.domain.model.Book
import com.example.bookapp.shared.data.local.BookDatabase

class BookLocalDataSource(private val db: BookDatabase) {

    private val queries = db.bookEntityQueries

    fun getAllBooks(): List<Book> = queries.getAllBooks().executeAsList().map {
        Book(
            id = it.id.toInt(),
            title = it.title,
            description = it.description,
            pageCount = it.pageCount.toInt(),
            excerpt = it.excerpt,
            publishDate = it.publishDate
        )
    }

    fun insertBook(book: Book) {
        queries.insertBook(
            id = book.id.toLong(),
            title = book.title,
            description = book.description,
            pageCount = book.pageCount.toLong(),
            excerpt = book.excerpt,
            publishDate = book.publishDate
        )
    }

    fun deleteBook(id: Int) {
        queries.deleteBook(id.toLong())
    }
}