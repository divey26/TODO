package com.example.notessqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notessqlite.databinding.ActivityUpdateNoteBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db: NoteDatabaseHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)

        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1) {
            finish()
            return
        }

        val note = db.getNoteByID(noteId)
        binding.updateTitleEditText.setText(note.title)
        binding.updateContentEditText.setText(note.content)
        binding.updatePriorityEditText.setText(note.priority.toString())

        // Format the due date before setting it in the EditText field
        val sdf = SimpleDateFormat("MM-dd", Locale.getDefault())
        val formattedDueDate = sdf.format(Date(note.dueDate))
        binding.updateDueDateEditText.setText(formattedDueDate)

        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()
            val priority = binding.updatePriorityEditText.text.toString().toIntOrNull() ?: 1

            if (priority == null || priority !in 0..4) {
                Toast.makeText(this, "Priority must be between 0 and 4", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val dueDateText = binding.updateDueDateEditText.text.toString()

            // Check if the date text is empty
            if (dueDateText.isEmpty()) {
                // Inform the user that the date field cannot be empty
                Toast.makeText(this, "Please enter a due date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Exit the function
            }

            // Parse the due date from the input text
            val dueDate: Date? = try {
                sdf.parse(dueDateText)
            } catch (e: ParseException) {
                // If parsing fails, inform the user about the expected date format
                Toast.makeText(this, "Please enter the date in MM-dd format", Toast.LENGTH_SHORT).show()
                null // Return null as the parsed date
            }

            // Check if the due date is null (i.e., parsing failed)
            if (dueDate == null) {
                return@setOnClickListener // Exit the function
            }

            val updateNote = Note(noteId, newTitle, newContent, priority, dueDate.time)
            db.updateNote(updateNote)
            finish()
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
        }
    }
}
