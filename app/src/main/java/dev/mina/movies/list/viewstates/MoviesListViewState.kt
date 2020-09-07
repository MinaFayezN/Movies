package dev.mina.movies.list.viewstates

import androidx.databinding.BaseObservable
import dev.mina.movies.data.Movie
import dev.mina.movies.list.adapters.MoviesAdapter
import dev.mina.movies.list.listeners.MovieItemClickListener

class MoviesListViewState(movieItemClickListener: MovieItemClickListener) :
    BaseObservable() {

    val adapter: MoviesAdapter = MoviesAdapter(movieItemClickListener)

    fun updateList(moviesList: List<Movie>) {
        adapter.updateList(moviesList)
    }
}