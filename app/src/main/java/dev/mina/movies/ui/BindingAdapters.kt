package dev.mina.movies.ui

import androidx.databinding.BindingAdapter
import me.gujun.android.taggroup.TagGroup

@BindingAdapter("addTags")
fun addTags(tagGroup: TagGroup, genres: ArrayList<String>) {
    tagGroup.setTags(genres)
}

