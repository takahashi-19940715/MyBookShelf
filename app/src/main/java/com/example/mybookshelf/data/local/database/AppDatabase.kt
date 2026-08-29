package com.example.mybookshelf.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mybookshelf.data.local.dao.BookDao
import com.example.mybookshelf.data.local.entity.BookEntity

@Database(
    entities = [BookEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao
}