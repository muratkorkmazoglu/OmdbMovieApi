package com.murat.movieapp.di

import android.util.Log
import com.murat.movieapp.domain.MovieListUseCases
import com.murat.movieapp.model.MovieApi
import com.murat.movieapp.model.Repository
import com.murat.movieapp.domain.MovieListInteractor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton
import java.io.IOException


const val SCHEDULER_MAIN_THREAD = "mainThread"
const val SCHEDULER_IO = "io"

@Module(includes = [ViewModelModule::class])
class AppModule {

    private val MOVIE_API_ENDPOINT = "http://www.omdbapi.com"

    @Singleton
    @Provides
    internal fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
    }

    @Singleton
    @Provides
    internal fun provideOkHttpClient(): OkHttpClient {

        return OkHttpClient.Builder().addNetworkInterceptor(LoggingInterceptor()).build()
    }

    @Singleton
    @Provides
    internal fun provideMovieApi(gson: Gson, client: OkHttpClient): MovieApi {

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(MOVIE_API_ENDPOINT)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    @Named(SCHEDULER_MAIN_THREAD)
    fun provideAndroidMainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Named(SCHEDULER_IO)
    fun provideIoScheduler(): Scheduler = Schedulers.io()

    @Provides
    fun providesMovieListUseCases(repository: Repository): MovieListUseCases = MovieListInteractor(repository)

    internal inner class LoggingInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()

            val t1 = System.nanoTime()
            Log.e("LOGLOG",
                String.format(
                    "Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.body()
                )
            )

            val response = chain.proceed(request)

            val t2 = System.nanoTime()
            Log.e("LOGLOG",
                String.format(
                    "Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6, response.body()
                )
            )

            return response
        }
    }
}