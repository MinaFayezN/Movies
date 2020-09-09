package dev.mina.movies.injection.component

import dagger.Component
import dev.mina.movies.details.viewmodels.SearchViewModel
import dev.mina.movies.injection.module.NetworkModule
import javax.inject.Singleton


@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {


    fun inject(searchViewModel: SearchViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}