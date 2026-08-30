package com.example.mybookshelf.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybookshelf.data.local.entity.BookEntity
import com.example.mybookshelf.data.local.entity.toBook
import com.example.mybookshelf.data.model.Book
import com.example.mybookshelf.data.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookViewModel(
    private val repository: BookRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(BookUiState())
    val uiState: StateFlow<BookUiState> = _uiState

    val books: StateFlow<List<Book>> =
        repository.getAllBooks()
            .map { entities ->
                entities.map { it.toBook() }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    // 編集中の本のID
    private var editingBookId: Int? = null

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
        val book = BookEntity(
            title = _uiState.value.title,
            author = _uiState.value.author,
            status = _uiState.value.status
        )

        viewModelScope.launch {
            repository.insertBook(book)
        }
    }

    fun startEditing(book: Book) {
        editingBookId = book.id

        _uiState.value = BookUiState(
            title = book.title,
            author = book.author,
            status = book.status
        )
    }

    fun updateBook() {
        val id = editingBookId ?: return

        val book = BookEntity(
            id = id,
            title = _uiState.value.title,
            author = _uiState.value.author,
            status = _uiState.value.status
        )

        viewModelScope.launch {
            repository.updateBook(book)
        }
    }
}