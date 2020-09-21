package dev.mina.movies.list.viewstates

import androidx.databinding.BaseObservable
import dev.mina.movies.list.adapters.MoviesAdapter

class MoviesListViewState : BaseObservable() {

    lateinit var adapter: MoviesAdapter
}