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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GHRepoViewModel @Inject constructor(
    private val ghRepoRepository: GHRepoRepository, private val ghRepoListMapper: GHRepoListMapper
) : ViewModel() {

    private val _apiState = MutableStateFlow<ApiState>(ApiState.Loading)
    val apiState = _apiState.asStateFlow()

    private val _allGhRepo: MutableList<GHRepo> = mutableListOf()
    private val _searchQuery = MutableStateFlow(String.EMPTY)

    private val _searchedGhRepo = MutableStateFlow<List<GHRepo>>(emptyList())
    val searchedGhRepo: StateFlow<List<GHRepo>> = _searchedGhRepo


    fun getUser() = viewModelScope.launch {
        ghRepoRepository.getGHRepo().asResult().collect {
            when (it) {
                is Result.Error -> {
                    _apiState.emit(ApiState.Error(it.exception?.message ?: String.EMPTY))
                }

                Result.Loading -> {
                    _apiState.emit(ApiState.Loading)
                }

                is Result.Success -> {
                    val data = ghRepoListMapper.map(it.data)
                    _allGhRepo.clear()
                    _allGhRepo.addAll(ghRepoListMapper.map(it.data))
                    _searchedGhRepo.value = data
                    _apiState.emit(ApiState.Success)
                }
            }
        }
    }

    fun search(searchText: String) {
        _searchQuery.value = searchText
        viewModelScope.launch {
            _searchQuery.debounce(timeoutMillis = 300).distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isBlank()) {
                        _searchedGhRepo.value = _allGhRepo
                    } else {
                        _searchedGhRepo.value = _allGhRepo.filter {
                            it.name.contains(query, ignoreCase = true) || it.id.toString()
                                .contains(query, ignoreCase = true)
                        }
                    }
                }
        }
    }
}