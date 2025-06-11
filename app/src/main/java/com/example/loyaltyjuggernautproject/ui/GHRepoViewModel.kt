package com.example.loyaltyjuggernautproject.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loyaltyjuggernautproject.core.EMPTY
import com.example.loyaltyjuggernautproject.core.states.ApiState
import com.example.loyaltyjuggernautproject.core.states.Result
import com.example.loyaltyjuggernautproject.core.states.asResult
import com.example.loyaltyjuggernautproject.data.GHRepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GHRepoViewModel @Inject constructor(
    private val GHRepoRepository: GHRepoRepository, private val ghRepoListMapper: GHRepoListMapper
) : ViewModel() {

    private val _user = MutableStateFlow<ApiState<List<GHRepo>>>(ApiState.Loading)
    val user = _user.asStateFlow()

    fun getUser() = viewModelScope.launch {
        GHRepoRepository.getGHRepo().asResult().collect {
            when (it) {
                is Result.Error -> {
                    _user.emit(ApiState.Error(it.exception?.message ?: String.EMPTY))
                }

                Result.Loading -> {
                    _user.emit(ApiState.Loading)
                }

                is Result.Success -> {
                    _user.emit(ApiState.Success(ghRepoListMapper.map(it.data)))
                }
            }
        }
    }
}