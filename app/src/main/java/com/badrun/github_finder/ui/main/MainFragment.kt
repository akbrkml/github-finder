package com.badrun.github_finder.ui.main

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.badrun.github_finder.R
import com.badrun.github_finder.base.BaseFragment
import com.badrun.github_finder.data.Resource
import com.badrun.github_finder.databinding.MainFragmentBinding
import com.badrun.github_finder.utils.EndlessRecyclerViewScrollListener
import com.badrun.github_finder.utils.ext.hideSoftKeyboard
import com.badrun.github_finder.utils.ext.setOnTextChanged
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainFragment : BaseFragment<MainFragmentBinding>() {

    private val viewModel: MainViewModel by viewModels()

    private var coroutineJob: Job? = null

    private var currentKeyword: String = ""
    private var currentPage = 1

    private lateinit var mainAdapter: MainAdapter
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.githubUserList.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    mainAdapter.showLoading()
                }
                is Resource.Success -> {
                    mainAdapter.hideLoading()
                    mainAdapter.setData(it.data!!)
                }
                is Resource.Error -> {
                    mainAdapter.hideLoading()
                    setMessage(it.message ?: getString(R.string.label_unexpected_error))
                }
                is Resource.Empty -> {
                    mainAdapter.hideLoading()
                    if (currentPage == 1) setMessage(getString(R.string.msg_not_found))
                }
            }
        })
    }

    override fun onCreated(view: View) {
        setupRecyclerView()
        setupSearchView()
    }

    private fun setupRecyclerView() {
        mainAdapter = MainAdapter()
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.apply {
            adapter = mainAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.searchUsers(currentKeyword, page)
                currentPage = page
            }
        }

        binding.recyclerView.addOnScrollListener(scrollListener)

    }

    private fun setMessage(message: String) {
        Snackbar.make(binding.main, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setupSearchView() {
        binding.searchView.setOnTextChanged { s, _, _, _ ->
            if (currentKeyword != s.toString()) {
                coroutineJob?.cancel()
                coroutineJob = GlobalScope.launch(Dispatchers.Main) {
                    delay(800)
                    currentKeyword = s.toString()
                    mainAdapter.clear()
                    scrollListener.resetState()
                    currentPage = 1
                    viewModel.searchUsers(s.toString(), currentPage)
                    requireActivity().hideSoftKeyboard()
                }
            }
        }

        binding.searchView.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                requireActivity().hideSoftKeyboard()
            }
            false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineJob?.cancel()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> MainFragmentBinding
        get() = MainFragmentBinding::inflate

}