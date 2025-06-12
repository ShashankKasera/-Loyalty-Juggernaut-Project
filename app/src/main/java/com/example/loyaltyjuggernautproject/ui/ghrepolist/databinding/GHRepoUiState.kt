package com.example.loyaltyjuggernautproject.ui.ghrepolist.databinding

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.example.loyaltyjuggernautproject.core.EMPTY
import com.example.loyaltyjuggernautproject.ui.ghrepolist.GHRepo

class GHRepoUiState : BaseObservable() {

    private var _ghRepoList: List<GHRepo> = emptyList()

    @get:Bindable
    var ghRepoList: List<GHRepo>
        get() = _ghRepoList
        set(value) {
            _ghRepoList = value
            notifyPropertyChanged(BR.ghRepoList)
        }

    @get:Bindable
    var ghRepoListVisibility: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.ghRepoListVisibility)
        }

    @get:Bindable
    var progressBarVisibility: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressBarVisibility)
        }

    @get:Bindable
    var errorMassageVisibility: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.errorMassageVisibility)
        }

    @get:Bindable
    var errorMassage: String = String.EMPTY
        set(value) {
            field = value
            notifyPropertyChanged(BR.errorMassage)
        }
}