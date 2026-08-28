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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mybookshelf.ui.theme.MyBookShelfTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyBookShelfTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BookListScreen(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class Book(
    val title: String,
    val author: String,
    val status: String
)

val sampleBooks = listOf(
    Book(
        title = "Android開発入門",
        author = "〇〇〇〇",
        status = "読書中"
    ),
    Book(
        title = "kotlin入門",
        author = "△△△△",
        status = "読了"
    ),
    Book(
        title = "Java入門",
        author = "□□□",
        status = "未読"
    ),
    Book(
        title = "Java入門",
        author = "□□□",
        status = "未読"
    ),
    Book(
        title = "Java入門",
        author = "□□□",
        status = "未読"
    ),
    Book(
        title = "Java入門",
        author = "□□□",
        status = "未読"
    ),
    Book(
        title = "Java入門",
        author = "□□□",
        status = "未読"
    ),
    Book(
        title = "Java入門",
        author = "□□□",
        status = "未読"
    ),
    Book(
        title = "Java入門",
        author = "□□□",
        status = "未読"
    )
)

@Composable
fun BookListScreen(name: String, modifier: Modifier = Modifier) {
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
                    onClick = {
                        // 後で本追加画面へ移動する
                    }
                ) {
                    Text(
                        text = "+"
                    )
                }
            }
        }

        items(sampleBooks) {book ->
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
        BookListScreen("Android")
    }
}