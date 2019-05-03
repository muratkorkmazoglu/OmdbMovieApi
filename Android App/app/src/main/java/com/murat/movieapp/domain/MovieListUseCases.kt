package com.murat.movieapp.domain

import android.os.Parcel
import android.os.Parcelable
import com.murat.movieapp.model.MovieResponse
import com.murat.movieapp.model.Ratings
import io.reactivex.Single

interface MovieListUseCases {

    fun getSearchMovieList(title: String, year: String): Single<MovieResponse>
}

data class MovieViewModel(
    val title: String?,
    val year: String?,
    val rated: String?,
    val released: String?,
    val runtime: String?,
    val genre: String?,
    val director: String?,
    val writer: String?,
    val actors: String?,
    val plot: String?,
    val language: String?,
    val country: String?,
    val awards: String?,
    val poster: String?,
    val ratings: List<Ratings>?,
    val metascore: String?,
    val imdbRating: String?,
    val imdbVotes: String?,
    val imdbID: String?,
    val type: String?,
    val dVD: String?,
    val boxOffice: String?,
    val production: String?,
    val website: String?,
    val response: String?
)