package dev.mina.movies.room

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.mina.movies.data.Movie


@Dao
interface MoviesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovie(movie: Movie)

    @Update
    fun updateMovie(movie: Movie)

    @Delete
    fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM movies WHERE title = :title")
    fun retrieveMovie(title: String): LiveData<Movie>

    @Query("SELECT * FROM movies")
    fun retrieveAllMovies(): LiveData<List<Movie>>

}