package com.example.mybookshelf.ui.screen.booklist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
    onAddBookClick: () -> Unit,
    onBookClick: (Book) -> Unit
) {
    val books by viewModel.books.collectAsStateWithLifecycle()

    BookListContent(
        books = books,
        onAddBookClick = onAddBookClick,
        onBookClick = onBookClick
    )
}

@Composable
fun BookListContent(
    books: List<Book>,
    onAddBookClick: () -> Unit,
    onBookClick: (Book) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            FloatingActionButton(
                onClick = onAddBookClick,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "追加"
                )
            }

            Text(
                text = "MyBookShelf",
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (books.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "まだ本が登録されていません"
                )

                Text(
                    text = "＋ボタンから本を追加してください"
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(books) { book ->
                    Card(
                        onClick = { onBookClick(book) },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = book.title,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(
                                modifier = Modifier.height(16.dp)
                            )

                            Text(
                                text = "著者：${book.author}",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Text(
                                text = "ステータス：${book.status}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
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
            onAddBookClick = {},
            onBookClick = {}
        )
    }
}