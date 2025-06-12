package com.example.loyaltyjuggernautproject.ui.ghrepolist.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loyaltyjuggernautproject.ui.ghrepolist.GHRepo
import com.example.loyaltyjuggernautproject.ui.ghrepolist.GHRepoAdapter

@BindingAdapter("ghRepoListAdapter", "ghRepoListData", requireAll = true)
fun bindGhRepoList(
    recyclerView: RecyclerView, adapter: GHRepoAdapter, data: List<GHRepo>
) {
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.adapter = adapter
    adapter.updateList(data)
}