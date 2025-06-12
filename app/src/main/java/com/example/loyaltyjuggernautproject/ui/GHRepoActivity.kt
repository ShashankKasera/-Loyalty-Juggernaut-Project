package com.example.loyaltyjuggernautproject.ui

import android.os.Bundle
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loyaltyjuggernautproject.R
import com.example.loyaltyjuggernautproject.core.gone
import com.example.loyaltyjuggernautproject.core.states.ApiState
import com.example.loyaltyjuggernautproject.core.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GHRepoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var loader: ProgressBar
    private lateinit var errorMassage: TextView
    private lateinit var etSearch: EditText
    private lateinit var ghRepoAdapter: GHRepoAdapter
    private val ghRepoViewModel: GHRepoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gh_repo)

        recyclerView = findViewById(R.id.user_list)
        loader = findViewById(R.id.loader)
        errorMassage = findViewById(R.id.error_massage)
        etSearch = findViewById(R.id.et_search)

        etSearch.addTextChangedListener { editable ->
            val query = editable?.toString()?.trim()
            ghRepoViewModel.search(query ?: "")
        }

        setUpRecyclerView()
        lifecycleScope.launch {
            ghRepoViewModel.apiState.collect {
                when (it) {
                    is ApiState.Error -> {
                        errorMassage.visible()
                        errorMassage.text = it.msg
                        loader.gone()
                    }

                    ApiState.Loading -> {
                        errorMassage.gone()
                        loader.visible()
                    }

                    ApiState.Success -> {
                        loader.gone()
                        errorMassage.gone()
                    }
                }
            }
        }
        ghRepoViewModel.getUser()

        lifecycleScope.launch {
            ghRepoViewModel.searchedGhRepo.collectLatest {
                if (it.size > 0) {
                    recyclerView.visible()
                    errorMassage.gone()
                    ghRepoAdapter.updateList(it)
                } else {
                    recyclerView.gone()
                    errorMassage.visible()
                    errorMassage.text = getString(R.string.no_data_found)
                }
            }
        }

    }

    private fun setUpRecyclerView() {
        ghRepoAdapter = GHRepoAdapter(this, ghRepoViewModel.searchedGhRepo.value.toMutableList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ghRepoAdapter
    }
}