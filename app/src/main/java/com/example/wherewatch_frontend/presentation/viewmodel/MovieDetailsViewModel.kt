package com.example.wherewatch_frontend.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wherewatch_frontend.domain.model.Availability
import com.example.wherewatch_frontend.domain.model.Country
import com.example.wherewatch_frontend.domain.model.Movie
import com.example.wherewatch_frontend.domain.model.Platform
import com.example.wherewatch_frontend.domain.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for loading movie details by ID and managing filtering options for
 * platform and country availability.
 *
 * Provides state flows for the movie data, loading state, error messages, selected platform and
 * country filters, and the filtered availability list.
 *
 * @param repository The repository used to fetch movie details.
 */
class MovieDetailsViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    /** Holds the currently loaded movie details or null if none loaded. */
    private val _movie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> = _movie

    /** Indicates whether a movie details loading operation is in progress. */
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    /** Holds an error message if loading fails, or null otherwise. */
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    /** The set of currently selected platforms to filter availabilities. */
    private val _selectedPlatforms = MutableStateFlow<Set<Platform>>(emptySet())
    val selectedPlatforms: StateFlow<Set<Platform>> = _selectedPlatforms

    /** The set of currently selected countries to filter availabilities. */
    private val _selectedCountries = MutableStateFlow<Set<Country>>(emptySet())
    val selectedCountries: StateFlow<Set<Country>> = _selectedCountries

    /**
     * A combined StateFlow that filters the movie's availabilities by the selected platforms and countries.
     * Emits an empty list if no movie is loaded.
     */
    val filteredAvailabilities: StateFlow<List<Availability>> = combine(
        _movie,
        _selectedPlatforms,
        _selectedCountries
    ) { movie, selectedPlatforms, selectedCountries ->
        movie?.availabilities?.filter { availability ->
            (selectedPlatforms.isEmpty() || availability.platform in selectedPlatforms) &&
                    (selectedCountries.isEmpty() || availability.country in selectedCountries)
        } ?: emptyList()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    /**
     * Loads the movie details by its unique [id].
     *
     * Updates loading and error states accordingly.
     *
     * @param id The ID of the movie to load.
     */
    fun loadMovieById(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val movie = repository.searchMovieById(id)
                _movie.value = movie
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error desconocido"
                _movie.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Toggles the selection state of a [platform] in the filter set.
     *
     * Adds the platform if it is not selected, removes it otherwise.
     *
     * @param platform The platform to toggle.
     */
    fun togglePlatform(platform: Platform) {
        _selectedPlatforms.value = _selectedPlatforms.value.toMutableSet().apply {
            if (!add(platform)) remove(platform)
        }
    }

    /**
     * Toggles the selection state of a [country] in the filter set.
     *
     * Adds the country if it is not selected, removes it otherwise.
     *
     * @param country The country to toggle.
     */
    fun toggleCountry(country: Country) {
        _selectedCountries.value = _selectedCountries.value.toMutableSet().apply {
            if (!add(country)) remove(country)
        }
    }

    /**
     * Clears all selected platform and country filters.
     */
    fun clearFilters() {
        _selectedPlatforms.value = emptySet()
        _selectedCountries.value = emptySet()
    }

}
