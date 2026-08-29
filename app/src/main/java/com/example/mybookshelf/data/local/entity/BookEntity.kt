package com.example.mybookshelf.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mybookshelf.data.model.Book

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,

    val author: String,

    val status: String
)

fun BookEntity.toBook(): Book {
    return Book(
        title = title,
        author = author,
        status = status
    )
}