package com.example.loyaltyjuggernautproject.ui.ghrepolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loyaltyjuggernautproject.databinding.GhRepoItemBinding

class GHRepoAdapter(
    private val context: GHRepoActivity,
    private val ghRepoList: MutableList<GHRepo>,
    private val onRepoClick: (String) -> Unit
) : RecyclerView.Adapter<GHRepoAdapter.ViewHolder>() {

    fun updateList(newList: List<GHRepo>) {
        ghRepoList.clear()
        ghRepoList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = GhRepoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ghRepo = ghRepoList[position]
        holder.bind(ghRepo, onRepoClick)
    }

    override fun getItemCount(): Int {
        return ghRepoList.size
    }

    class ViewHolder(private val binding: GhRepoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ghRepo: GHRepo, onClick: (String) -> Unit) {
            binding.ghRepo = ghRepo
            binding.onRepoClick = onClick
            binding.executePendingBindings()
        }
    }
}