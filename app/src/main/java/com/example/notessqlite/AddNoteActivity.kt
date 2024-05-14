package com.example.notessqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notessqlite.databinding.ActivityAddNoteBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: NoteDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)

        binding.saveButton.setOnClickListener{
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val priority = 1 // Set the priority value as needed
            val dueDateText = binding.dueDateEditText.text.toString()

            if (dueDateText.isEmpty()) {
                Toast.makeText(this, "Please enter a due date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Exit the function
            }

            // Parse the due date from the input text
            val sdf = SimpleDateFormat("MM-dd", Locale.getDefault())
            val dueDate: Date? = try {
                sdf.parse(dueDateText)
            } catch (e: ParseException) {
                Toast.makeText(this, "Please enter the date in MM-dd format", Toast.LENGTH_SHORT).show()
                null
            }

            if (dueDate == null) {
                return@setOnClickListener
            }

            val note = Note(0, title, content, priority, dueDate.time)
            db.insertNote(note)
            finish()
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
        }
    }
}
