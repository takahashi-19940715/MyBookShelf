package com.example.mybookshelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.mybookshelf.data.local.database.AppDatabase
import com.example.mybookshelf.data.repository.BookRepository
import com.example.mybookshelf.ui.screen.bookadd.BookAddScreen
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
                    }
                }
            }
        }
    }
}

@Composable
fun BookListScreen(
    modifier: Modifier = Modifier,
    viewModel: BookViewModel,
    onAddBookClick: () -> Unit
) {
    val books by viewModel.books.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MyBookShelf",
                    modifier = Modifier.padding(16.dp)
                )

                IconButton(
                    onClick = onAddBookClick
                ) {
                    Text(
                        text = "+"
                    )
                }
            }
        }

        items(books) { book ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
                ) {
                    Text(
                        text = book.title
                    )

                    Text(
                        text = "著者：${book.author}",
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Text(
                        text = "ステータス：${book.status}",
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookListScreenPreview() {
    MyBookShelfTheme {
        val bookViewModel: BookViewModel = viewModel()

        BookListScreen(
            viewModel = bookViewModel,
            onAddBookClick = {}
        )
    }
}