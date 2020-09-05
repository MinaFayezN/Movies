package dev.mina.movies.db

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import dev.mina.movies.room.MOVIES_DATABASE_NAME
import dev.mina.movies.room.MoviesDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.util.concurrent.TimeUnit

abstract class DbTest {
    @Rule
    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()
    private lateinit var _db: MoviesDatabase
    val db: MoviesDatabase
        get() = _db

    @Before
    fun initDb() {
        _db = Room.databaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MoviesDatabase::class.java,
            MOVIES_DATABASE_NAME
        ).build()
    }

    @After
    fun closeDb() {
        countingTaskExecutorRule.drainTasks(10, TimeUnit.SECONDS)
        _db.close()
    }
}