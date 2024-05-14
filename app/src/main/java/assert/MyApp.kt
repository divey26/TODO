package assert


import android.app.Application
import androidx.room.Room

class MyApp : Application() {
    companion object {
        lateinit var database: NoteDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext, NoteDatabase::class.java, "notes_database")
            .build()
    }
}
