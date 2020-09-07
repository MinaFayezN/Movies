package dev.mina.movies.list.listeners

import dev.mina.movies.data.Movie

interface MovieItemClickListener {
    fun onMovieItemClicked(movie: Movie)
}
