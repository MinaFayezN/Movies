package dev.mina.movies.repo

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import dev.mina.movies.data.Movie
import dev.mina.movies.room.MoviesDAO

class MoviesRepository(private val moviesDAO: MoviesDAO) {

    fun getAllMovies(): LiveData<List<Movie>> {
        return moviesDAO.retrieveAllMovies()
    }

    fun searchMovies(query: String): DataSource.Factory<Int, Movie> {
        return moviesDAO.searchMovies(query)
    }

    fun retriveAllMoviesGrouped(): DataSource.Factory<Int, Movie> {
        return moviesDAO.retrieveAllMoviesGrouped()
    }

    fun addMovie(movie: Movie) {
        moviesDAO.addMovie(movie)
    }

    fun addAllMovies(movies: List<Movie>) {
        moviesDAO.addAllMovies(movies)
    }

}