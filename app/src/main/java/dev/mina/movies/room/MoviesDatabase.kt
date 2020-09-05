package dev.mina.movies.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.mina.movies.data.AllMovies
import dev.mina.movies.data.Movie
import dev.mina.movies.util.readFileFromAssets
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val MOVIES_DATABASE_NAME = "MoviesDB"
const val MOVIES_JSON_FILE_NAME = "AllMovies.json"

@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDAO

    private class MoviesCallBack(
        val context: Context,
        val coroutineScope: CoroutineScope
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            instance?.let { moviesDatabase ->
                coroutineScope.launch {
                    val dao = moviesDatabase.moviesDao()
                    insertAllMovies(dao)
                }
            }
        }

        private suspend fun insertAllMovies(moviesDao: MoviesDAO) {
            withContext(Dispatchers.IO) {
                val movies = readFileFromAssets<AllMovies>(
                    context,
                    MOVIES_JSON_FILE_NAME
                )
                    ?.movies ?: ArrayList()
                moviesDao.addAllMovies(movies)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: MoviesDatabase? = null

        fun getInstance(context: Context, coroutineScope: CoroutineScope): MoviesDatabase {
            val tempInstance = instance
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDatabase::class.java,
                    MOVIES_DATABASE_NAME
                )
                    .addCallback(MoviesCallBack(context.applicationContext, coroutineScope))
                    .build()
                this.instance = instance
                return instance
            }
        }

    }


}

