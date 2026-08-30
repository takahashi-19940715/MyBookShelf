package com.example.mybookshelf.data.repository

import com.example.mybookshelf.data.local.dao.BookDao
import com.example.mybookshelf.data.local.entity.BookEntity
import kotlinx.coroutines.flow.Flow

class BookRepository(
    private val bookDao: BookDao
) {
    fun getAllBooks(): Flow<List<BookEntity>> {
        return bookDao.getAllBooks()
    }

    suspend fun insertBook(book: BookEntity) {
        bookDao.insertBook(book)
    }

    suspend fun updateBook(book: BookEntity) {
        bookDao.updateBook(book)
    }

    suspend fun deleteBook(book: BookEntity) {
        bookDao.deleteBook(book)
    }
}