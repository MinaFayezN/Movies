package dev.mina.movies.list.viewmodels

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import dev.mina.movies.data.Movie
import dev.mina.movies.repo.MoviesRepository
import dev.mina.movies.room.MoviesDAO
import dev.mina.movies.room.MoviesDatabase

class MoviesListViewModel(application: Application) : AndroidViewModel(application) {

    private val moviesRepository: MoviesRepository
    private val moviesDAO: MoviesDAO = MoviesDatabase
        .getInstance(application, viewModelScope)
        .moviesDao()
    var allMoviesList: LiveData<PagedList<Movie>>? = null
    var filterTextAll = MutableLiveData("")

    init {
        moviesRepository = MoviesRepository(moviesDAO)
        initAllMovies()
    }


    private fun initAllMovies() {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .build()
        allMoviesList = Transformations.switchMap(filterTextAll) { input: String? ->
            if (input == null || input == "") {
                return@switchMap LivePagedListBuilder(moviesDAO.retrieveAllMoviesGrouped(), config)
                    .build()
            } else {
                return@switchMap LivePagedListBuilder(moviesDAO.searchMovies(input), config)
                    .build()
            }
        }
    }
}