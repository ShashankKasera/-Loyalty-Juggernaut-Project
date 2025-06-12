package com.example.loyaltyjuggernautproject.ui.ghrepolist

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.loyaltyjuggernautproject.R
import com.example.loyaltyjuggernautproject.core.states.ApiState
import com.example.loyaltyjuggernautproject.databinding.ActivityGhRepoBinding
import com.example.loyaltyjuggernautproject.ui.ghrepodetail.GHRepoDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GHRepoActivity : AppCompatActivity() {

    private val ghRepoViewModel: GHRepoViewModel by viewModels()
    private val adapter by lazy {
        GHRepoAdapter(this, mutableListOf()) { repoUrl ->
            val intent = Intent(this, GHRepoDetailActivity::class.java).apply {
                putExtra(getString(R.string.repo_url), repoUrl)
            }
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityGhRepoBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@GHRepoActivity
            viewModel = ghRepoViewModel
            ghRepoUiState = ghRepoViewModel.ghRepoUiState
            adapter = this@GHRepoActivity.adapter
        }

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        ghRepoViewModel.getUser()
        observeApiState()
    }

    private fun observeApiState() {
        lifecycleScope.launch {
            ghRepoViewModel.apiState.collect { state ->
                when (state) {
                    is ApiState.Loading -> {
                        ghRepoViewModel.ghRepoUiState.apply {
                            progressBarVisibility = true
                            errorMassageVisibility = false
                        }
                    }

                    is ApiState.Error -> {
                        ghRepoViewModel.ghRepoUiState.apply {
                            progressBarVisibility = false
                            errorMassageVisibility = true
                            errorMassage = state.msg
                        }
                    }

                    is ApiState.Success -> {
                        ghRepoViewModel.ghRepoUiState.apply {
                            progressBarVisibility = false
                            errorMassageVisibility = false
                            observeSearchResults()
                        }
                    }
                }
            }
        }
    }

    private fun observeSearchResults() {
        lifecycleScope.launch {
            ghRepoViewModel.searchedGhRepo.collectLatest { list ->
                ghRepoViewModel.ghRepoUiState.apply {
                    ghRepoListVisibility = true
                    if (list.isNotEmpty()) {
                        errorMassageVisibility = false
                        ghRepoListVisibility = true
                        adapter.updateList(list)
                    } else {
                        errorMassageVisibility = true
                        ghRepoListVisibility = false
                        errorMassage = getString(R.string.no_data_found)
                    }
                }
            }
        }
    }
}
