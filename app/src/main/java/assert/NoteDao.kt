package assert
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notessqlite.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: assert.Note)

    @Query("SELECT * FROM allnotes")
    fun getAllNotes(): Flow<List<Note>> // Return a Flow instead of a List

    @Update
    suspend fun update(note: assert.Note)

    @Query("SELECT * FROM allnotes WHERE id = :noteId")
    suspend fun getNoteById(noteId: Int): Note?

    @Query("DELETE FROM allnotes WHERE id = :noteId")
    suspend fun deleteById(noteId: Int)
}
