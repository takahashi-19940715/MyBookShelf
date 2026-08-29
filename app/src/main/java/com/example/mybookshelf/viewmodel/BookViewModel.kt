package com.example.mybookshelf.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mybookshelf.data.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BookViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(BookUiState())
    val uiState: StateFlow<BookUiState> = _uiState

    private val _books = MutableStateFlow(
        listOf(
            Book(
                title = "Android開発入門",
                author = "〇〇〇〇",
                status = "読書中"
            ),
            Book(
                title = "kotlin入門",
                author = "△△△△",
                status = "読了"
            ),
            Book(
                title = "Java入門",
                author = "□□□",
                status = "未読"
            )
        )
    )
    val books: StateFlow<List<Book>> = _books

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun updateAuthor(author: String) {
        _uiState.value = _uiState.value.copy(author = author)
    }

    fun updateStatus(status: String) {
        _uiState.value = _uiState.value.copy(status = status)
    }

    fun addBook() {
        val newBook = Book(
            title = _uiState.value.title,
            author = _uiState.value.author,
            status = _uiState.value.status
        )

        _books.value += newBook
    }
}