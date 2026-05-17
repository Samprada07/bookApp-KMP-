package com.example.bookapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform