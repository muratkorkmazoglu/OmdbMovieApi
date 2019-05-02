package com.murat.movieapp.di

import com.murat.movieapp.view.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    internal abstract fun contributeSearchFragment(): SearchFragment
}