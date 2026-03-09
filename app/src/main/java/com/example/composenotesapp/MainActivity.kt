package com.example.composenotesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composenotesapp.data.Note
import com.example.composenotesapp.data.NoteRepository
import com.example.composenotesapp.ui.theme.ComposeNotesTheme
import androidx.compose.animation.animateContentSize

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ComposeNotesTheme {

                var showDialog by remember { mutableStateOf(false) }
                var title by remember { mutableStateOf("") }
                var description by remember { mutableStateOf("") }

                Scaffold(

                    topBar = {
                        NotesTopBar()
                    },

                    floatingActionButton = {

                        FloatingActionButton(
                            onClick = { showDialog = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Note"
                            )
                        }

                    }

                ) { padding ->

                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        NotesScreen()

                    }

                }

                if (showDialog) {

                    AlertDialog(

                        onDismissRequest = { showDialog = false },

                        title = {
                            Text("Add Note")
                        },

                        text = {

                            Column {

                                OutlinedTextField(
                                    value = title,
                                    onValueChange = { title = it },
                                    label = { Text("Title") }
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = description,
                                    onValueChange = { description = it },
                                    label = { Text("Description") }
                                )

                            }

                        },

                        confirmButton = {

                            TextButton(

                                onClick = {

                                    NoteRepository.notes.add(
                                        Note(
                                            id = NoteRepository.notes.size + 1,
                                            title = title,
                                            description = description
                                        )
                                    )

                                    title = ""
                                    description = ""
                                    showDialog = false
                                }

                            ) {

                                Text("Add")

                            }

                        },

                        dismissButton = {

                            TextButton(
                                onClick = { showDialog = false }
                            ) {

                                Text("Cancel")

                            }

                        }

                    )

                }

            }

        }

    }

}
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun NotesTopBar() {

    TopAppBar(
        title = {
            Text(
                text = "Compose Notes",
                style = MaterialTheme.typography.titleLarge
            )
        }
    )

}

@Composable
fun NotesScreen() {

    val notes = NoteRepository.notes

    LazyColumn {

        items(notes) { note ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .animateContentSize()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = note.title,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f)
                        )

                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.clickable {
                                NoteRepository.notes.remove(note)
                            }
                        )

                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = note.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

        }

    }

}