package dev.mina.movies.api

import dev.mina.movies.data.ImagesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchAPI {

    @GET("services/rest/")
    fun searchForImages(
        @Query("method")
        method: String = "flickr.photos.search",
        @Query("api_key")
        apiKey: String = "efb8ade18fc9b8a20dcd50ee5aaa08f5",
        @Query("format")
        format: String = "json",
        @Query("nojsoncallback")
        noJSONCallback: Int = 1,
        @Query("safe_search")
        safeSearch: Int = 1,
        @Query("text")
        text: String
    ): Single<ImagesResponse>
}