package dev.mina.movies.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(
	tableName = "movies",
	indices = [Index(name = "movie_title", value = ["title"])]
)
@Parcelize
data class Movie(
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	var id: Int=0,
	@SerializedName("title")
	@ColumnInfo(name = "title")
	var title: String,

	@SerializedName("year")
	@ColumnInfo(name = "year")
	var year: Int,

	@SerializedName("cast")
	@ColumnInfo(name = "cast")
	var cast: ArrayList<String>,

	@SerializedName("genres")
	@ColumnInfo(name = "genres")
	var genres: ArrayList<String>,

	@SerializedName("rating")
	@ColumnInfo(name = "rating")
	var rating: Int
) : Parcelable