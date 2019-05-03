package com.murat.movieapp.view

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.murat.movieapp.di.SCHEDULER_IO
import com.murat.movieapp.di.SCHEDULER_MAIN_THREAD
import com.murat.movieapp.domain.MovieListUseCases
import com.murat.movieapp.model.MovieResponse
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class SearchViewModel
@Inject constructor(
    private val movieListUseCases: MovieListUseCases,
    @Named(SCHEDULER_IO) val subscribeOnScheduler: Scheduler,
    @Named(SCHEDULER_MAIN_THREAD) val observeOnScheduler: Scheduler
) : ViewModel() {
    val searchMovieLiveData = MutableLiveData<MovieResponse>()
    private val compositeDisposable = CompositeDisposable()

    fun getSearchMoviesList(title: String, year: String = "") {
        compositeDisposable.add(
            movieListUseCases.getSearchMovieList(title, year)
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
                .subscribe(this::onSearchMovieListReceived)
        )
    }

    private fun onSearchMovieListReceived(movieList: MovieResponse) {
        searchMovieLiveData.value = movieList
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}