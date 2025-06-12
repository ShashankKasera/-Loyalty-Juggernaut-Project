package com.example.loyaltyjuggernautproject.ui.ghrepolist

import com.example.loyaltyjuggernautproject.core.EMPTY
import java.io.Serializable

data class GHRepo(
    val id: Long = 0L, val name: String = String.EMPTY, val repoURL: String = String.EMPTY
) : Serializable