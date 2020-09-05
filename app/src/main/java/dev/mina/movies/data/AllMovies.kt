package dev.mina.movies.data

import com.google.gson.annotations.SerializedName


data class AllMovies(

	@SerializedName("movies") val movies: List<Movie>
)