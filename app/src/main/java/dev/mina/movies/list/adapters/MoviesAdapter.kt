package dev.mina.movies.list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.mina.movies.data.Movie
import dev.mina.movies.databinding.MovieItemBinding
import dev.mina.movies.list.listeners.MovieItemClickListener
import dev.mina.movies.list.viewstates.MovieItemViewState

class MoviesAdapter(
    private val movieItemClickListener: MovieItemClickListener,
    private val values: ArrayList<Movie> = ArrayList()
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(MovieItemViewState(movieItemClickListener, item))
    }

    override fun getItemCount(): Int = values.size

    fun updateList(movies: List<Movie>) {
        values.clear()
        values.addAll(movies)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewState: MovieItemViewState) {
            binding.viewState = viewState
            binding.executePendingBindings()
        }

    }
}