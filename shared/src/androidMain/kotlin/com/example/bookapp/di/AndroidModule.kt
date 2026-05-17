package com.example.bookapp.di

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.bookapp.shared.data.local.BookDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {
    single {
        val driver = AndroidSqliteDriver(
            schema = BookDatabase.Schema,
            context = androidContext(),
            name = "book.db"
        )
        BookDatabase(driver)
    }
}