package com.example.mybookshelf.ui.screen.bookadd

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mybookshelf.ui.theme.MyBookShelfTheme
import com.example.mybookshelf.viewmodel.BookViewModel

@Composable
fun BookAddScreen(
    viewModel: BookViewModel,
    onBookAdded: () -> Unit,
    onBackClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.resetUiState()
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BookAddContent(
        title = uiState.title,
        author = uiState.author,
        status = uiState.status,
        titleError = uiState.titleError,
        authorError = uiState.authorError,
        onTitleChange = { viewModel.updateTitle(it) },
        onAuthorChange = { viewModel.updateAuthor(it) },
        onStatusChange = { viewModel.updateStatus(it) },
        onAddBook = {
            viewModel.addBook(
                onComplete = { onBookAdded() }
            )
        },
        onBackClick = { onBackClick() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAddContent(
    title: String,
    author: String,
    status: String,
    titleError: String?,
    authorError: String?,
    onTitleChange: (String) -> Unit,
    onAuthorChange: (String) -> Unit,
    onStatusChange: (String) -> Unit,
    onAddBook: () -> Unit,
    onBackClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

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
                onClick = { onBackClick() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "戻る"
                )
            }

            Text(
                text = "本を追加",
                modifier = Modifier.align(Alignment.Center)
            )
        }

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
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
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
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
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

            Button(
                onClick = onAddBook,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("登録")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookAddScreenPreview() {
    MyBookShelfTheme {
        BookAddContent(
            title = "Android開発入門",
            author = "サンプル著者",
            status = "読書中",
            titleError = null,
            authorError = null,
            onTitleChange = {},
            onAuthorChange = {},
            onStatusChange = {},
            onAddBook = {},
            onBackClick = {}
        )
    }
}