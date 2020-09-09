package dev.mina.movies.base

import androidx.lifecycle.ViewModel
import dev.mina.movies.details.viewmodels.SearchViewModel
import dev.mina.movies.injection.component.DaggerViewModelInjector
import dev.mina.movies.injection.component.ViewModelInjector
import dev.mina.movies.injection.module.NetworkModule


open class BaseViewModel : ViewModel() {

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }


    private fun inject() {
        when (this) {
            is SearchViewModel -> injector.inject(this)
        }
    }
}