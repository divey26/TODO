package assert


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "allnotes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val priority: Int,
    val dueDate: Long
)
