package com.example.loyaltyjuggernautproject.ui

import android.os.Bundle
import android.util.Log
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
import com.example.loyaltyjuggernautproject.data.remote.networkmodel.User
import com.example.loyaltyjuggernautproject.data.remote.networkmodel.UserResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var loader: ProgressBar
    lateinit var errorMassage: TextView
    private val userViewModel: UserViewModel by viewModels()
    lateinit var userAdapter: UserAdapter
    private var userList = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.user_list)
        loader = findViewById(R.id.loader)
        errorMassage = findViewById(R.id.error_massage)

        setUpRecyclerView()
        lifecycleScope.launch {
            userViewModel.user.collect {
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

                    is ApiState.Success<UserResponse> -> {
                        loader.gone()
                        errorMassage.gone()
                        userList.addAll(it.data.items.toMutableList())
                        userAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
        userViewModel.getUser()
    }

    private fun setUpRecyclerView() {
        userAdapter = UserAdapter(userList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userAdapter
    }
}