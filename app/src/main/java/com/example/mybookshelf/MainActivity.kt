package com.example.mybookshelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.mybookshelf.data.local.database.AppDatabase
import com.example.mybookshelf.data.repository.BookRepository
import com.example.mybookshelf.ui.screen.bookadd.BookAddScreen
import com.example.mybookshelf.ui.screen.bookedit.BookEditScreen
import com.example.mybookshelf.ui.screen.booklist.BookListScreen
import com.example.mybookshelf.ui.theme.MyBookShelfTheme
import com.example.mybookshelf.viewmodel.BookViewModel
import com.example.mybookshelf.viewmodel.BookViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "book_database"
            ).build()
            val bookDao = db.bookDao()
            val repository = BookRepository(bookDao)
            val factory = BookViewModelFactory(repository)
            val bookViewModel: BookViewModel = viewModel(
                factory = factory
            )

            MyBookShelfTheme {
                val navController = rememberNavController()
                val bookViewModel: BookViewModel = bookViewModel

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "book_list",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("book_list") {
                            BookListScreen(
                                viewModel = bookViewModel,
                                onAddBookClick = {
                                    navController.navigate("book_add")
                                },
                                onBookClick = { book ->
                                    bookViewModel.startEditing(book)
                                    navController.navigate("book_edit")
                                }
                            )
                        }

                        composable("book_add") {
                            BookAddScreen(
                                viewModel = bookViewModel,
                                onBookAdded = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable("book_edit") {
                            BookEditScreen(
                                viewModel = bookViewModel,
                                onBookUpdated = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}