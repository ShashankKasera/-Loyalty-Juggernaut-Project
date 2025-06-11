package com.example.loyaltyjuggernautproject.core.states

sealed interface ApiState<out T> {
    object Loading : ApiState<Nothing>
    data class Error(val msg: String) : ApiState<Nothing>
    data class Success<T>(val data: T) : ApiState<T>
}