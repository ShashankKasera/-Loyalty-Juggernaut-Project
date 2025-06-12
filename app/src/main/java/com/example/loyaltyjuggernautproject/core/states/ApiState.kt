package com.example.loyaltyjuggernautproject.core.states

sealed interface ApiState {
    object Loading : ApiState
    data class Error(val msg: String) : ApiState
    object Success : ApiState
}