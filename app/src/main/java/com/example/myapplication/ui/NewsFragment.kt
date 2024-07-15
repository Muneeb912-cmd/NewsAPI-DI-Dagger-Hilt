package com.example.myapplication.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresExtension
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.myapplication.com.example.myapplication.adapters.NewsAdapter
import com.example.myapplication.com.example.myapplication.utilities.NetworkUtils
import com.example.myapplication.com.example.myapplication.viewModel.NewsViewModel
import com.example.myapplication.utilities.Resource

import com.example.myapplication.databinding.FragmentNewsBinding
import com.example.myapplication.repositories.Article
import com.example.myapplication.repositories.NewsResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment(), NewsAdapter.OnItemClickListener {

    private val viewModel: NewsViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView
    private lateinit var binding: FragmentNewsBinding
    private lateinit var refreshButton: Button

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
        override fun onAvailable(network: Network) {
            // Network is available, proceed with fetching data
            activity?.runOnUiThread {
                fetchNews()
            }
        }

        override fun onLost(network: Network) {
            // Network is lost, show error message
            activity?.runOnUiThread {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.GONE
                errorTextView.visibility = View.VISIBLE
                errorTextView.text = "No internet connection"
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        bindViews()
        refreshButton.setOnClickListener {
            fetchNews()
        }
        observeNewsApi()
        return binding.root
    }

    private fun bindViews() {
        recyclerView = binding.recyclerView
        progressBar = binding.progressBar
        errorTextView = binding.errorTextView
        refreshButton = binding.refreshButton
    }

    private fun observeNewsApi() {
        viewModel.news.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Success -> {
                    displayData(resource)
                }

                is Resource.Error -> {
                    displayError(resource)
                }

                is Resource.Loading -> {
                    displayLoading()
                }
            }
        })
    }

    private fun displayLoading() {
        // Show loading and hide error
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        errorTextView.visibility = View.GONE
    }

    private fun displayError(resource: Resource<NewsResponse>) {
        // Hide loading and show error
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        errorTextView.visibility = View.VISIBLE
        refreshButton.visibility = View.VISIBLE
        errorTextView.text = resource.message ?: "An error occurred"
    }

    private fun displayData(resource: Resource<NewsResponse>) {
        // Hide loading and error
        progressBar.visibility = View.GONE
        errorTextView.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        refreshButton.visibility = View.GONE
        // Handle success
        resource.data?.let {
            setUpRecyclerView(it.articles)
        }
    }

    override fun onStart() {
        super.onStart()
        NetworkUtils.registerNetworkCallback(requireContext(), networkCallback)
        if (!NetworkUtils.isNetworkAvailable(requireContext())) {
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.GONE
            errorTextView.visibility = View.VISIBLE
            errorTextView.text = "No internet connection"
        }
    }

    override fun onStop() {
        super.onStop()
        NetworkUtils.unregisterNetworkCallback(requireContext(), networkCallback)
    }

    private fun setUpRecyclerView(articles: List<Article>) {
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = NewsAdapter(articles, this)
        recyclerView.adapter = adapter
    }

    override fun onButtonClick(title: String) {
        // Handle button click
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun fetchNews() {
        viewModel.fetchNews("tesla", "90aba61021f44215937abcae831d2128")
    }
}
