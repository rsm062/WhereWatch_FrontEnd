package com.example.wherewatch_frontend.presentation.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel {
    private val _searchData = MutableStateFlow("")
    val searchData: StateFlow<String> = _searchData
}