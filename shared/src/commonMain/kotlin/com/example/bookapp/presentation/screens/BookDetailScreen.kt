package com.example.bookapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bookapp.presentation.viewmodel.BookDetailViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(bookId: Int, onBack: () -> Unit) {
    val viewModel = koinViewModel<BookDetailViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(bookId) {
        viewModel.getBook(bookId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when {
                state.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                state.error != null -> Text(state.error ?: "Error", modifier = Modifier.align(Alignment.Center))
                state.book != null -> {
                    val book = state.book!!
                    Column(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(book.title, style = MaterialTheme.typography.headlineMedium)
                        Text("Pages: ${book.pageCount}", style = MaterialTheme.typography.bodyMedium)
                        Text("Published: ${book.publishDate}", style = MaterialTheme.typography.bodySmall)
                        HorizontalDivider()
                        Text(book.description, style = MaterialTheme.typography.bodyMedium)
                        Text(book.excerpt, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}