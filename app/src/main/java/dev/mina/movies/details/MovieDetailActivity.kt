package dev.mina.movies.details

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dev.mina.movies.R
import dev.mina.movies.data.Movie
import dev.mina.movies.list.MovieListActivity


class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(findViewById(R.id.detail_toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        if (savedInstanceState == null) {
            val fragment = MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(
                        MovieDetailFragment.ARG_ITEM,
                        intent.getParcelableExtra<Movie>(MovieDetailFragment.ARG_ITEM)
                    )
                }
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.movie_detail_container, fragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                navigateUpTo(Intent(this, MovieListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}