package dev.mina.movies.list

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dev.mina.movies.R
import dev.mina.movies.data.Movie
import dev.mina.movies.databinding.ActivityMovieListBinding
import dev.mina.movies.details.MovieDetailActivity
import dev.mina.movies.details.MovieDetailFragment
import dev.mina.movies.list.listeners.MovieItemClickListener
import dev.mina.movies.list.viewmodels.MoviesListViewModel
import dev.mina.movies.list.viewstates.MoviesListViewState


class MovieListActivity : AppCompatActivity(), MovieItemClickListener {

    private val moviesListViewState: MoviesListViewState by lazy {
        MoviesListViewState(this)
    }
    private val viewModel: MoviesListViewModel by viewModels()

    private var twoPane: Boolean = false
    private lateinit var binding: ActivityMovieListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDataBinding()
        initToolBar()
        observeOnMovieList()
        twoPane = binding.lMoviesList.movieDetailContainer != null
    }

    private fun observeOnMovieList() {
        viewModel.getAllMovies().observe(this, {
            moviesListViewState.updateList(it)
        })
    }

    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView<ActivityMovieListBinding>(
            this,
            R.layout.activity_movie_list
        ).apply {
            lifecycleOwner = this@MovieListActivity
            viewState = moviesListViewState
        }

    }

    private fun initToolBar() {
        binding.toolbar.apply {
            title = title
            setSupportActionBar(this)
        }
    }


    override fun onMovieItemClicked(movie: Movie) {
        if (twoPane) {
            val fragment = MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(MovieDetailFragment.ARG_ITEM, movie)
                }
            }
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.movie_detail_container, fragment)
                .commit()
        } else {
            val intent = Intent(this, MovieDetailActivity::class.java).apply {
                putExtra(MovieDetailFragment.ARG_ITEM, movie)
            }
            startActivity(intent)
        }
    }

}