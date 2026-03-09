package com.example.composenotesapp.data

import androidx.compose.runtime.mutableStateListOf

object NoteRepository {

    val notes = mutableStateListOf(
        Note(1, "Shopping List", "Buy milk, eggs, and bread"),
        Note(2, "Workout Plan", "Chest workout at 6 PM"),
        Note(3, "Project Idea", "Build a Compose Notes App")
    )

}