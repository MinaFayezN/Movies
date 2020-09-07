package dev.mina.movies.list.viewstates

import android.view.View
import androidx.databinding.BaseObservable
import dev.mina.movies.data.Movie
import dev.mina.movies.list.listeners.MovieItemClickListener

class MovieItemViewState(
    private val movieItemClickListener: MovieItemClickListener,
    val movie: Movie
) : BaseObservable() {

    fun onMovieItemClicked(view: View) {
        movieItemClickListener.onMovieItemClicked(view.tag as Movie)
    }
}
