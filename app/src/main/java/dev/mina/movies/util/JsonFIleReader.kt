package dev.mina.movies.util

import android.content.Context
import com.google.gson.Gson

inline fun <reified T> readFileFromAssets(context: Context, fileName: String): T? {
    val jsonString =
        context.assets.open(fileName).bufferedReader().use { it.readText() }
    return Gson().fromJson(jsonString, T::class.java)
}