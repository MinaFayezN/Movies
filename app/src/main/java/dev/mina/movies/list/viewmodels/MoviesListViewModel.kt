package dev.mina.movies.list.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dev.mina.movies.data.Movie
import dev.mina.movies.repo.MoviesRepository
import dev.mina.movies.room.MoviesDatabase

class MoviesListViewModel(application: Application) : AndroidViewModel(application) {

    private val moviesRepository: MoviesRepository


    init {
        val moviesDAO = MoviesDatabase
            .getInstance(application, viewModelScope)
            .moviesDao()
        moviesRepository = MoviesRepository(moviesDAO)
    }

    fun getAllMovies(): LiveData<List<Movie>> {
        return moviesRepository.getAllMovies()
    }


}