package dev.mina.movies.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import dev.mina.movies.R
import me.gujun.android.taggroup.TagGroup

@BindingAdapter("addTags")
fun addTags(tagGroup: TagGroup, genres: ArrayList<String>) {
    tagGroup.setTags(genres)
}

@BindingAdapter("loadURL")
fun loadURL(view: ImageView, url: String) {
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.ic_loading)
        .error(R.drawable.ic_no_photo)
        .into(view)
}
