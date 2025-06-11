package com.example.loyaltyjuggernautproject.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GHRepoEntity(
    @PrimaryKey(autoGenerate = true) var id: Long, val name: String, val html_url: String
)