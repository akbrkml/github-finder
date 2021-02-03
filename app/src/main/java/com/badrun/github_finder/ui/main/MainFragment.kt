package com.badrun.github_finder.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.badrun.github_finder.R
import com.badrun.github_finder.base.BaseFragment
import com.badrun.github_finder.data.Resource
import com.badrun.github_finder.databinding.MainFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainFragmentBinding>() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var mainAdapter: MainAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.githubUserList.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    hideLoading()
                    mainAdapter.submitList(it.data)
                }
                is Resource.Error -> {
                    hideLoading()
                    setMessage(it.message ?: getString(R.string.label_unexpected_error))
                }
            }
        })
    }

    override fun onCreated(view: View) {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        mainAdapter = MainAdapter()
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = mainAdapter
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun setMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> MainFragmentBinding
        get() = MainFragmentBinding::inflate

}