package com.example.wherewatch_frontend.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
* ViewModel responsible for managing the search query state in the SearchScreen.
*
* It holds a [MutableStateFlow] to represent the current search input text and exposes it as a
* read-only [StateFlow] to observers.
*
* Provides methods to update the search data and clear it.
*/
class SearchViewModel : ViewModel() {
    private val _searchData = MutableStateFlow("")
    val searchData: StateFlow<String> = _searchData

    /**
     * Updates the current search data with the provided [data].
     *
     * @param data The new search string entered by the user.
     */
    fun setSearchData(data: String) {
        _searchData.value = data

    }

    /**
     * Clears the current search data, resetting it to an empty string.
     */
    fun clearSearchData() {
        _searchData.value = ""
    }
}
