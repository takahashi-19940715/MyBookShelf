package com.example.mybookshelf.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BookViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(BookUiState())
    val uiState: StateFlow<BookUiState> = _uiState

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun updateAuthor(author: String) {
        _uiState.value = _uiState.value.copy(author = author)
    }

    fun updateStatus(status: String) {
        _uiState.value = _uiState.value.copy(status = status)
    }
}