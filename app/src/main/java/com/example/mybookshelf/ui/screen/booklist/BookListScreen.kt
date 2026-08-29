package com.example.mybookshelf.ui.screen.booklist

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mybookshelf.data.model.Book
import com.example.mybookshelf.ui.theme.MyBookShelfTheme
import com.example.mybookshelf.viewmodel.BookViewModel

@Composable
fun BookListScreen(
    viewModel: BookViewModel,
    onAddBookClick: () -> Unit
) {
    val books by viewModel.books.collectAsStateWithLifecycle()

    BookListContent(
        books = books,
        onAddBookClick = onAddBookClick
    )
}

@Composable
fun BookListContent(
    books: List<Book>,
    onAddBookClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
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
        val sampleBooks = listOf(
            Book(
                id = 1,
                title = "Android開発入門",
                author = "サンプル著者",
                status = "読書中"
            ),
            Book(
                id = 2,
                title = "Kotlin入門",
                author = "サンプル著者",
                status = "未読"
            )
        )

        BookListContent(
            books = sampleBooks,
            onAddBookClick = {}
        )
    }
}