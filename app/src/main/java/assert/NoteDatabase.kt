package assert

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "notes_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

    // Define suspend functions to call DAO methods using coroutines
    suspend fun insert(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao().insert(note)
        }
    }

    fun getAllNotes() = noteDao().getAllNotes()

    suspend fun update(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao().update(note)
        }
    }

    suspend fun getNoteById(noteId: Int) = noteDao().getNoteById(noteId)

    suspend fun deleteById(noteId: Int) {
        withContext(Dispatchers.IO) {
            noteDao().deleteById(noteId)
        }
    }
}
