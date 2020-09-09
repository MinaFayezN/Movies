package dev.mina.movies.list

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import dev.mina.movies.R
import dev.mina.movies.data.Movie
import dev.mina.movies.databinding.ActivityMovieListBinding
import dev.mina.movies.details.MovieDetailActivity
import dev.mina.movies.details.MovieDetailFragment
import dev.mina.movies.list.listeners.MovieItemClickListener
import dev.mina.movies.list.viewmodels.MoviesListViewModel
import dev.mina.movies.list.viewstates.MoviesListViewState


class MovieListActivity : AppCompatActivity(), MovieItemClickListener,
    SearchView.OnQueryTextListener {

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_menu, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView? = searchItem?.actionView as SearchView

        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView?.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }


}