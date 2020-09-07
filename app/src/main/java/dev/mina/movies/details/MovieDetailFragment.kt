package dev.mina.movies.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import dev.mina.movies.R
import dev.mina.movies.data.Movie


class MovieDetailFragment : Fragment() {


    private var item: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM)) {
                item = it.getParcelable(ARG_ITEM)
                activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title =
                    item?.title
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.movie_detail, container, false)

        item?.let {
            rootView.findViewById<TextView>(R.id.movie_detail).text = it.title
        }

        return rootView
    }

    companion object {
        const val ARG_ITEM = "item"
    }
}