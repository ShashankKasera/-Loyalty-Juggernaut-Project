package com.example.loyaltyjuggernautproject.ui

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loyaltyjuggernautproject.R
import com.example.loyaltyjuggernautproject.core.gone
import com.example.loyaltyjuggernautproject.core.states.ApiState
import com.example.loyaltyjuggernautproject.core.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GHRepoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var loader: ProgressBar
    private lateinit var errorMassage: TextView
    private lateinit var ghRepoAdapter: GHRepoAdapter
    private val ghRepoViewModel: GHRepoViewModel by viewModels()
    private var ghRepoList = mutableListOf<GHRepo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gh_repo)

        recyclerView = findViewById(R.id.user_list)
        loader = findViewById(R.id.loader)
        errorMassage = findViewById(R.id.error_massage)

        setUpRecyclerView()
        lifecycleScope.launch {
            ghRepoViewModel.user.collect {
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

                    is ApiState.Success<List<GHRepo>> -> {
                        loader.gone()
                        errorMassage.gone()
                        ghRepoList.addAll(it.data.toMutableList())
                        ghRepoAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
        ghRepoViewModel.getUser()
    }

    private fun setUpRecyclerView() {
        ghRepoAdapter = GHRepoAdapter(ghRepoList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ghRepoAdapter
    }
}