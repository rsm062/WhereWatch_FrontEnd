package com.example.wherewatch_frontend.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel : ViewModel() {
    private val _searchData = MutableStateFlow("")
    val searchData: StateFlow<String> = _searchData

    fun setSearchData(data: String) {
        _searchData.value = data

    }
    fun clearSearchData() {
        _searchData.value = ""
    }
}
