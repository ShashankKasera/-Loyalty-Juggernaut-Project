package com.example.loyaltyjuggernautproject.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loyaltyjuggernautproject.core.EMPTY
import com.example.loyaltyjuggernautproject.core.states.ApiState
import com.example.loyaltyjuggernautproject.core.states.Result
import com.example.loyaltyjuggernautproject.core.states.asResult
import com.example.loyaltyjuggernautproject.data.UserRepository
import com.example.loyaltyjuggernautproject.data.remote.networkmodel.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(val userRepository: UserRepository) : ViewModel() {

    private val _user = MutableStateFlow<ApiState<UserResponse>>(ApiState.Loading)
    val user = _user.asStateFlow()

    fun getUser() = viewModelScope.launch {
        userRepository.getUser().asResult().collect {
            when (it) {
                is Result.Error -> {
                    _user.emit(ApiState.Error(it.exception?.message ?: String.EMPTY))
                }

                Result.Loading -> {
                    _user.emit(ApiState.Loading)
                }

                is Result.Success -> {
                    _user.emit(ApiState.Success(it.data))
                }
            }
        }
    }
}