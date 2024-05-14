package com.example.notessqlite

class Note(
    val id: Int,
    val title: String,
    val content: String,
    val priority: Int,
    val dueDate: Long // Assuming dueDate is stored as milliseconds since epoch
)
