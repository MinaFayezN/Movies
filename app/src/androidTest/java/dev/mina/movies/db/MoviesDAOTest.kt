package dev.mina.movies.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.mina.movies.data.AllMovies
import dev.mina.movies.data.Movie
import dev.mina.movies.room.MOVIES_JSON_FILE_NAME
import dev.mina.movies.util.readFileFromAssets
import dev.mina.movies.utils.DBTestUtils
import dev.mina.movies.utils.getOrAwaitValue
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoviesDAOTest : DbTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun insertAllAndRead() {
        val movies = readFileFromAssets<AllMovies>(
            ApplicationProvider.getApplicationContext(),
            MOVIES_JSON_FILE_NAME
        )
            ?.movies ?: ArrayList()
        db.moviesDao().addAllMovies(movies)
        val loadedMovies = db.moviesDao().retrieveAllMovies().getOrAwaitValue()
        MatcherAssert.assertThat(loadedMovies, CoreMatchers.notNullValue())
        Assert.assertTrue(loadedMovies.size > 2000)
    }

    @Test
    fun insertAndRead() {
        val movie: Movie = DBTestUtils.createTestMovie(
            title = "Test Movie Title",
            year = 2020,
            cast = arrayListOf("Test Cast 01", "Test Cast 02"),
            genres = arrayListOf("Test Genre 01", "Test Genre 02"),
            rating = 4
        )
        db.moviesDao().addMovie(movie)
        val loadedMovie = db.moviesDao().retrieveMovie("Test Movie Title").getOrAwaitValue()
        MatcherAssert.assertThat(loadedMovie, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(loadedMovie.title, CoreMatchers.`is`("Test Movie Title"))
        MatcherAssert.assertThat(loadedMovie.year, CoreMatchers.`is`(2020))
        MatcherAssert.assertThat(
            loadedMovie.cast,
            CoreMatchers.`is`(arrayListOf("Test Cast 01", "Test Cast 02"))
        )
        MatcherAssert.assertThat(
            loadedMovie.genres,
            CoreMatchers.`is`(arrayListOf("Test Genre 01", "Test Genre 02"))
        )
        MatcherAssert.assertThat(loadedMovie.rating, CoreMatchers.`is`(4))
    }

    @Test
    fun insertAndUpdateAndRead() {
        val movie: Movie = DBTestUtils.createTestMovie(
            title = "Test Movie Title for Update",
            year = 2020,
            cast = arrayListOf("Test Cast 01", "Test Cast 02"),
            genres = arrayListOf("Test Genre 01", "Test Genre 02"),
            rating = 4
        )
        db.moviesDao().addMovie(movie)
        val updatedMovie: Movie =
            db.moviesDao().retrieveMovie("Test Movie Title for Update").getOrAwaitValue()
        updatedMovie.year = 2021
        updatedMovie.cast = arrayListOf("Updated Test Cast 01", "Updated Test Cast 02")
        updatedMovie.genres = arrayListOf("Updated Test Genre 01", "Updated Test Genre 02")
        updatedMovie.rating = 5
        db.moviesDao().updateMovie(updatedMovie)
        val loadedMovie =
            db.moviesDao().retrieveMovie("Test Movie Title for Update").getOrAwaitValue()
        MatcherAssert.assertThat(loadedMovie, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(
            loadedMovie.title,
            CoreMatchers.`is`("Test Movie Title for Update")
        )
        MatcherAssert.assertThat(loadedMovie.year, CoreMatchers.`is`(2021))
        MatcherAssert.assertThat(
            loadedMovie.cast,
            CoreMatchers.`is`(arrayListOf("Updated Test Cast 01", "Updated Test Cast 02"))
        )
        MatcherAssert.assertThat(
            loadedMovie.genres,
            CoreMatchers.`is`(arrayListOf("Updated Test Genre 01", "Updated Test Genre 02"))
        )
        MatcherAssert.assertThat(loadedMovie.rating, CoreMatchers.`is`(5))
    }

}