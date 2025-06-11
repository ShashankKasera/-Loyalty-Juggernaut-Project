package com.example.loyaltyjuggernautproject.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loyaltyjuggernautproject.R

class GHRepoAdapter(
    private val ghRepoList: MutableList<GHRepo>
) : RecyclerView.Adapter<GHRepoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gh_repo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvId.text = ghRepoList[position].id.toString()
        holder.tvName.text = ghRepoList[position].name
        holder.tvUel.text = ghRepoList[position].repoURL
    }

    override fun getItemCount(): Int {
        return ghRepoList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvId: TextView = itemView.findViewById(R.id.tv_id)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvUel: TextView = itemView.findViewById(R.id.tv_url)
    }
}