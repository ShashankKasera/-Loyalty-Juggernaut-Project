package com.example.loyaltyjuggernautproject.ui.ghrepolist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loyaltyjuggernautproject.R
import com.example.loyaltyjuggernautproject.core.gone
import com.example.loyaltyjuggernautproject.core.states.ApiState
import com.example.loyaltyjuggernautproject.core.visible
import com.example.loyaltyjuggernautproject.databinding.ActivityGhRepoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GHRepoActivity : AppCompatActivity() {


    private lateinit var ghRepoAdapter: GHRepoAdapter
    private lateinit var binding: ActivityGhRepoBinding
    private val ghRepoViewModel: GHRepoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGhRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.etSearch.addTextChangedListener { editable ->
            val query = editable?.toString()?.trim()
            ghRepoViewModel.search(query ?: "")
        }

        setUpRecyclerView()
        lifecycleScope.launch {
            ghRepoViewModel.apiState.collect {
                when (it) {
                    is ApiState.Error -> {
                        binding.errorMassage.visible()
                        binding.errorMassage.text = it.msg
                        binding.loader.gone()
                    }

                    ApiState.Loading -> {
                        binding.errorMassage.gone()
                        binding.loader.visible()
                    }

                    ApiState.Success -> {
                        binding.loader.gone()
                        binding.errorMassage.gone()
                    }
                }
            }
        }
        ghRepoViewModel.getUser()

        lifecycleScope.launch {
            ghRepoViewModel.searchedGhRepo.collectLatest {
                if (it.size > 0) {
                    binding.ghRepoList.visible()
                    binding.errorMassage.gone()
                    ghRepoAdapter.updateList(it)
                } else {
                    binding.ghRepoList.gone()
                    binding.errorMassage.visible()
                    binding.errorMassage.text = getString(R.string.no_data_found)
                }
            }
        }

    }

    private fun setUpRecyclerView() {
        ghRepoAdapter = GHRepoAdapter(this, ghRepoViewModel.searchedGhRepo.value.toMutableList())
        binding.ghRepoList.layoutManager = LinearLayoutManager(this)
        binding.ghRepoList.adapter = ghRepoAdapter
    }
}