package dev.mina.movies.list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.mina.movies.data.Movie
import dev.mina.movies.databinding.MovieItemBinding
import dev.mina.movies.list.listeners.MovieItemClickListener
import dev.mina.movies.list.viewstates.MovieItemViewState

class MoviesAdapter(
    private val movieItemClickListener: MovieItemClickListener,
) : PagedListAdapter<Movie, MoviesAdapter.ViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(MovieItemViewState(movieItemClickListener, it))
        }
    }

    class ViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewState: MovieItemViewState) {
            binding.viewState = viewState
            binding.executePendingBindings()
        }

    }
}

private val DIFF_CALLBACK: DiffUtil.ItemCallback<Movie> =
    object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldMovie: Movie, newMovie: Movie): Boolean {
            return (oldMovie.id == newMovie.id)
        }

        override fun areContentsTheSame(oldMovie: Movie, newMovie: Movie): Boolean {
            return oldMovie.title == newMovie.title
        }
    }
