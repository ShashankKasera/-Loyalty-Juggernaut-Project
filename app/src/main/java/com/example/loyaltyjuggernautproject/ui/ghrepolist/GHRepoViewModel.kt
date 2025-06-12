package com.example.loyaltyjuggernautproject.ui.ghrepolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loyaltyjuggernautproject.core.EMPTY
import com.example.loyaltyjuggernautproject.core.states.ApiState
import com.example.loyaltyjuggernautproject.core.states.Result
import com.example.loyaltyjuggernautproject.core.states.asResult
import com.example.loyaltyjuggernautproject.data.GHRepoRepository
import com.example.loyaltyjuggernautproject.ui.ghrepolist.databinding.GHRepoUiState
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
    val apiState: StateFlow<ApiState> = _apiState.asStateFlow()

    private val _allGhRepo = mutableListOf<GHRepo>()

    private val _searchQuery = MutableStateFlow(String.EMPTY)
    fun search(searchText: String) {
        _searchQuery.value = searchText
    }

    private val _searchedGhRepo = MutableStateFlow<List<GHRepo>>(emptyList())
    val searchedGhRepo: StateFlow<List<GHRepo>> = _searchedGhRepo.asStateFlow()

    val ghRepoUiState = GHRepoUiState()

    init {
        observeSearch()
    }

    fun getUser() {
        viewModelScope.launch {
            ghRepoRepository.getGHRepo().asResult().collect { result ->
                when (result) {
                    is Result.Loading -> _apiState.emit(ApiState.Loading)

                    is Result.Success -> {
                        val data = ghRepoListMapper.map(result.data)
                        _allGhRepo.clear()
                        _allGhRepo.addAll(data)
                        _searchedGhRepo.value = data
                        _apiState.emit(ApiState.Success)
                    }

                    is Result.Error -> {
                        _apiState.emit(ApiState.Error(result.exception?.message.orEmpty()))
                    }
                }
            }
        }
    }

    private fun observeSearch() {
        viewModelScope.launch {
            _searchQuery.debounce(300).distinctUntilChanged().collectLatest { query ->
                _searchedGhRepo.value = if (query.isBlank()) {
                    _allGhRepo
                } else {
                    _allGhRepo.filter {
                        it.name.contains(query, ignoreCase = true) || it.id.toString()
                            .contains(query)
                    }
                }
            }
        }
    }
}
