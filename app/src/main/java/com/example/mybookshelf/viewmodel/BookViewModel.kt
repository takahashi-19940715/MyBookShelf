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
import kotlinx.coroutines.flow.update
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

    // 編集中の本情報
    private var editingBook: Book? = null

    fun updateTitle(title: String) {
        _uiState.update {
            it.copy(
                title = title,
                titleError = null
            )
        }
    }

    fun updateAuthor(author: String) {
        _uiState.update {
            it.copy(
                author = author,
                authorError = null
            )
        }
    }

    fun updateStatus(status: String) {
        _uiState.update {
            it.copy(status = status)
        }
    }

    fun addBook(): Boolean {
        val currentState = _uiState.value
        var hasError = false

        if (currentState.title.isBlank()) {
            _uiState.update {
                it.copy(titleError = "タイトルを入力してください")
            }
            hasError = true
        }

        if (currentState.author.isBlank()) {
            _uiState.update {
                it.copy(authorError = "著者を入力してください")
            }
            hasError = true
        }

        if (hasError) {
            return false
        }

        val bookEntity = BookEntity(
            title = currentState.title,
            author = currentState.author,
            status = currentState.status
        )

        viewModelScope.launch {
            repository.insertBook(bookEntity)
        }

        return true
    }

    fun startEditing(book: Book) {
        editingBook = book

        _uiState.value = BookUiState(
            title = book.title,
            author = book.author,
            status = book.status
        )
    }

    fun updateBook(): Boolean {
        val currentState = _uiState.value
        var hasError = false

        if (currentState.title.isBlank()) {
            _uiState.update {
                it.copy(titleError = "タイトルを入力してください")
            }
            hasError = true
        }

        if (currentState.author.isBlank()) {
            _uiState.update {
                it.copy(authorError = "著者を入力してください")
            }
            hasError = true
        }

        if (hasError) {
            return false
        }

        val book = editingBook ?: return false

        val bookEntity = BookEntity(
            id = book.id,
            title = currentState.title,
            author = currentState.author,
            status = currentState.status
        )

        viewModelScope.launch {
            repository.updateBook(bookEntity)
        }

        return true
    }

    fun deleteBook() {
        val book = editingBook ?:return

        val bookEntity = BookEntity(
            id =  book.id,
            title = book.title,
            author = book.author,
            status = book.status
        )

        viewModelScope.launch {
            repository.deleteBook(bookEntity)
        }
    }
}