package dev.mina.movies.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dev.mina.movies.data.Movie
import dev.mina.movies.databinding.MovieDetailBinding
import dev.mina.movies.details.adapters.PhotoListAdapter
import dev.mina.movies.details.viewmodels.SearchViewModel
import dev.mina.movies.details.viewmodels.TAG
import dev.mina.movies.list.viewstates.MovieItemViewState
import dev.mina.movies.util.addTo
import io.reactivex.disposables.CompositeDisposable


class MovieDetailFragment : Fragment() {


    private val viewModel: SearchViewModel by viewModels()

    private var item: Movie? = null
    private lateinit var binding: MovieDetailBinding
    private val photoListAdapter = PhotoListAdapter()
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_ITEM)) {
                item = it.getParcelable(ARG_ITEM)
            }
        }
        item?.let {
            viewModel.searchText.onNext(it.title)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MovieDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            this.viewModel = this@MovieDetailFragment.viewModel
            adapter = photoListAdapter
            item?.let {
                movieItemViewState = MovieItemViewState(movie = it).apply {
                    castVisibility = View.VISIBLE
                }
            }
        }
        subscribeToPhotoListSubject()
        observeOnTitle()
        return binding.root
    }

    private fun subscribeToPhotoListSubject() {
        viewModel
            .apiResponse
            .map { it.photos.photo }
            .subscribe(
                { list ->
                    photoListAdapter.updatePhotoList(list)
                },
                { throwable ->
                    Log.e(TAG, "Error in subscription for photoList:  $throwable")
                }
            ).addTo(disposables)
    }

    private fun observeOnTitle() {
        viewModel.title.observe(viewLifecycleOwner, {
            activity?.title = it
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        const val ARG_ITEM = "item"
    }
}