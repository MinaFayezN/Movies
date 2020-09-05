package dev.mina.movies.utils

import dev.mina.movies.data.Movie

object DBTestUtils {

    fun createTestMovie(
        title: String,
        year: Int,
        cast: ArrayList<String>,
        genres: ArrayList<String>,
        rating: Int
    ): Movie {
        return Movie(title = title, year = year, cast = cast, genres = genres, rating = rating)
    }
}