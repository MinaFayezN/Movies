package dev.mina.movies.list.viewstates

import android.view.View
import android.view.View.GONE
import androidx.databinding.BaseObservable
import dev.mina.movies.data.Movie
import dev.mina.movies.list.listeners.MovieItemClickListener

class MovieItemViewState(
    private val movieItemClickListener: MovieItemClickListener? = null,
    val movie: Movie
) : BaseObservable() {

    var castVisibility = GONE

    fun onMovieItemClicked(view: View) {
        movieItemClickListener?.onMovieItemClicked(view.tag as Movie)
    }

    val cast: String = StringBuilder().apply {
        movie.cast.forEach { append("\u2022 $it \n") }
    }.toString().trim()
}
