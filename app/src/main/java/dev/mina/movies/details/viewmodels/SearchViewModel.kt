package dev.mina.movies.details.viewmodels

import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.MutableLiveData
import dev.mina.movies.api.SearchAPI
import dev.mina.movies.base.BaseViewModel
import dev.mina.movies.data.ImagesResponse
import dev.mina.movies.util.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

const val TAG = "FlickrSearch"

class SearchViewModel : BaseViewModel() {

    @Inject
    lateinit var searchAPI: SearchAPI

    val searchText = BehaviorSubject.create<String>()
    val title = MutableLiveData("")
    val errorMessage = MutableLiveData("")
    val loadingVisibility = MutableLiveData(GONE)

    val apiResponse = BehaviorSubject.create<ImagesResponse>().apply {
        doOnNext {
            loadingVisibility.postValue(GONE)
            errorMessage.postValue(if (it.photos.photo.isEmpty()) "No Photos Found" else "")
        }
        doOnError {
            loadingVisibility.postValue(GONE)
            errorMessage.postValue(it.message ?: it.toString())
        }
    }
    private val disposables = CompositeDisposable()


    init {
        searchText
            .filter { it.isEmpty().not() }
            .doOnNext {
                loadingVisibility.postValue(VISIBLE)
                errorMessage.postValue("")
                title.postValue(it)
            }
            .subscribe(
                { text ->
                    searchAPI.searchForImages(text = text)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError { throwable ->
                            loadingVisibility.postValue(GONE)
                            errorMessage.postValue(throwable.message ?: throwable.toString())
                        }.doOnSuccess {
                            loadingVisibility.postValue(GONE)
                            errorMessage.postValue("")
                        }
                        .subscribe({ response ->
                            apiResponse.onNext(response)
                        }, { throwable ->
                            Log.e(TAG, "Error while calling SearchAPI: $throwable")
                        })

                },
                { throwable ->
                    Log.e(TAG, "Error in subscription for searchText:  $throwable")
                    errorMessage.postValue(throwable.message ?: throwable.toString())
                    loadingVisibility.postValue(GONE)
                }
            ).addTo(disposables)
    }


    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}