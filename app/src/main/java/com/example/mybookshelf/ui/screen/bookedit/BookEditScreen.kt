package com.example.mybookshelf.ui.screen.bookedit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mybookshelf.ui.theme.MyBookShelfTheme
import com.example.mybookshelf.viewmodel.BookViewModel

@Composable
fun BookEditScreen(
    viewModel: BookViewModel,
    onBookUpdated: () -> Unit,
    onBookDeleted: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BookEditContent(
        title = uiState.title,
        author = uiState.author,
        status = uiState.status,
        titleError = uiState.titleError,
        authorError = uiState.authorError,
        onTitleChange = { viewModel.updateTitle(it) },
        onAuthorChange = { viewModel.updateAuthor(it) },
        onStatusChange = { viewModel.updateStatus(it) },
        onUpdateBook = {
            viewModel.updateBook(
                onComplete = { onBookUpdated() }
            )
        },
        onDeleteBook = {
            viewModel.deleteBook(
                onComplete = { onBookDeleted() }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookEditContent(
    title: String,
    author: String,
    status: String,
    titleError: String?,
    authorError: String?,
    onTitleChange: (String) -> Unit,
    onAuthorChange: (String) -> Unit,
    onStatusChange: (String) -> Unit,
    onUpdateBook: () -> Unit,
    onDeleteBook: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "本を編集")

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
        OutlinedTextField(
            value = title,
            onValueChange = { onTitleChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("タイトル") },
            isError = titleError != null,
            supportingText = {
                titleError?.let {
                    Text(it)
                }
            }
        )
            OutlinedTextField(
                value = author,
                onValueChange = { onAuthorChange(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("著者") },
                isError = authorError != null,
                supportingText = {
                    authorError?.let {
                        Text(it)
                    }
                }
            )

            Text("ステータス")

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = status,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    }
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    val statusList = listOf(
                        "未読",
                        "読書中",
                        "読了"
                    )

                    statusList.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                onStatusChange(option)
                                expanded = false
                            }
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = onUpdateBook,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("保存")
                }

                Button(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("削除")
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
            },
            title = {
                Text("本を削除しますか？")
            },
            text = {
                Text("この本を削除すると元に戻せません。")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDeleteBook()
                    }
                ) {
                    Text("削除")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {
                    Text("キャンセル")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BookEditScreenPreview() {
    MyBookShelfTheme {
        BookEditContent(
            title = "Android開発入門",
            author = "サンプル著者",
            status = "読書中",
            titleError = null,
            authorError = null,
            onTitleChange = {},
            onAuthorChange = {},
            onStatusChange = {},
            onUpdateBook = {},
            onDeleteBook = {}
        )
    }
}