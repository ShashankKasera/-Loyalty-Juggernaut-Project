package com.example.loyaltyjuggernautproject.core

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: LoyaltyJuggernautDispatchers)

enum class LoyaltyJuggernautDispatchers {
    IO, MAIN, DEFAULT
}