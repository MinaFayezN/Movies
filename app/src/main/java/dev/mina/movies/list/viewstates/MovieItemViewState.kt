package dev.mina.movies.list.viewstates

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.BaseObservable
import dev.mina.movies.data.Movie
import dev.mina.movies.list.listeners.MovieItemClickListener

class MovieItemViewState(
    private val movieItemClickListener: MovieItemClickListener? = null,
    val movie: Movie
) : BaseObservable() {

    var titleVisibility = VISIBLE
    var castVisibility = GONE

    fun onMovieItemClicked(view: View) {
        movieItemClickListener?.onMovieItemClicked(view.tag as Movie)
    }
}
