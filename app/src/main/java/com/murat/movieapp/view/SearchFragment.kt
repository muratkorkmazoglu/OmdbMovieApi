package com.murat.movieapp.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.murat.movieapp.di.Injectable
import com.murat.movieapp.model.MovieResponse
import com.murat.movieapp.R
import com.murat.movieapp.common.MovieListRecyclerAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.search_fragment_layout.*
import kotlinx.android.synthetic.main.search_fragment_layout.view.*
import org.jetbrains.anko.design.longSnackbar
import javax.inject.Inject

class SearchFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SearchViewModel
    lateinit var movieListAdapter: MovieListRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.search_fragment_layout, container, false)
        activity?.actionBar?.show()
        initializeSearchView(view)
        initializeRecyclerView(view)
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
        observeViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.searchMovieLiveData.removeObserver(searchObserver)
    }

    private fun observeViewModel() {
        viewModel.searchMovieLiveData.observe(this, searchObserver)
    }

    private fun initializeSearchView(view: View) {
        view.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(title: String): Boolean {
                searchView.clearFocus()
                return if (title != "") {
                    viewModel.getSearchMoviesList(title)
                    true
                } else
                    false
            }
        })
    }

    private val searchObserver = Observer<MovieResponse> {
        if (it?.response.equals("False")) {
            searchList.visibility = View.GONE
            view?.longSnackbar("Arama Kriterlerinizde Film Bulunamadı.")
        } else {
            searchList.visibility = View.VISIBLE
            searchList.adapter = MovieListRecyclerAdapter(listOf(it))
        }
    }

    private fun initializeRecyclerView(view: View) {
        val linearLayoutManager = LinearLayoutManager(context)
        view.searchList.apply {
            layoutManager = linearLayoutManager
            movieListAdapter = MovieListRecyclerAdapter()
        }
    }
}