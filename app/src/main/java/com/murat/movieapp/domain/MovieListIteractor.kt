package com.murat.movieapp.domain

import com.murat.movieapp.domain.MovieListUseCases
import com.murat.movieapp.model.MovieResponse
import com.murat.movieapp.model.Repository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieListInteractor @Inject
constructor(private val repository: Repository) : MovieListUseCases {
    override fun getSearchMovieList(title: String, year: String): Single<MovieResponse> {
        return repository.getSearchMovies(title, year)
    }


}