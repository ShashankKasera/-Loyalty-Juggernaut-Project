package com.example.loyaltyjuggernautproject.core.states

sealed interface ApiState {
    data object Loading : ApiState
    data class Error(val msg: String) : ApiState
    data object Success : ApiState
}