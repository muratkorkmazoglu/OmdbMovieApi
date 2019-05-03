package com.murat.movieapp.model

import io.reactivex.Single

interface IRepository {
    fun getSearchMovies(title: String, year: String): Single<MovieResponse>
}