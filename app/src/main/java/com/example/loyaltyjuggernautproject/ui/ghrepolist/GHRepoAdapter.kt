package com.example.loyaltyjuggernautproject.ui.ghrepolist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.loyaltyjuggernautproject.R
import com.example.loyaltyjuggernautproject.ui.ghrepodetail.GHRepoDetailActivity

class GHRepoAdapter(
    private val context: GHRepoActivity, private val ghRepoList: MutableList<GHRepo>
) : RecyclerView.Adapter<GHRepoAdapter.ViewHolder>() {

    fun updateList(newList: List<GHRepo>) {
        ghRepoList.clear()
        ghRepoList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gh_repo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvId.text = context.getString(R.string.id, ghRepoList[position].id.toString())
        holder.tvName.text = context.getString(R.string.name, ghRepoList[position].name)
        holder.tvUel.text = context.getString(R.string.repoURL, ghRepoList[position].repoURL)

        holder.cvGHRepo.setOnClickListener {
            val intent = Intent(context, GHRepoDetailActivity::class.java)
            intent.putExtra(context.getString(R.string.repo_url), ghRepoList[position].repoURL)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return ghRepoList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvId: TextView = itemView.findViewById(R.id.tv_id)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvUel: TextView = itemView.findViewById(R.id.tv_url)
        val cvGHRepo: CardView = itemView.findViewById(R.id.cv_gh_repo)
    }
}