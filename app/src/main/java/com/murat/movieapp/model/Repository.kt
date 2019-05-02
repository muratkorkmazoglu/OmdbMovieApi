package com.murat.movieapp.model

import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject
constructor(private val movieApi: MovieApi) : IRepository {
    override fun getSearchMovies(title: String, year: String): Single<MovieResponse> =
        movieApi.getSearchMovies(title, year)
}