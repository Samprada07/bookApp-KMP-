package com.example.bookapp.di

import com.example.bookapp.data.local.BookLocalDataSource
import com.example.bookapp.data.remote.BookApiService
import com.example.bookapp.data.remote.createHttpClient
import com.example.bookapp.data.repository.BookRepositoryImpl
import com.example.bookapp.domain.repository.BookRepository
import com.example.bookapp.domain.usecase.AddBookUseCase
import com.example.bookapp.domain.usecase.DeleteBookUseCase
import com.example.bookapp.domain.usecase.GetBookByIdUseCase
import com.example.bookapp.domain.usecase.GetBooksUseCase
import org.koin.dsl.module

val appModule = module {
    single { createHttpClient() }
    single { BookApiService(get()) }
    single { BookLocalDataSource(get()) }
    single<BookRepository> { BookRepositoryImpl(get(), get()) }
    factory { GetBooksUseCase(get()) }
    factory { GetBookByIdUseCase(get()) }
    factory { AddBookUseCase(get()) }
    factory { DeleteBookUseCase(get()) }
}