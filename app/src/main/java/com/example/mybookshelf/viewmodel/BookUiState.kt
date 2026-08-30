package com.example.mybookshelf.viewmodel

data class BookUiState (
    val title: String = "",
    val author: String = "",
    val status: String = "未読",
    val titleError: String? = null,
    val authorError: String? = null
)