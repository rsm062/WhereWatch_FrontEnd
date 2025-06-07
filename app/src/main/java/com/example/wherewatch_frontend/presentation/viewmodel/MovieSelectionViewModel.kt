package com.example.wherewatch_frontend.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wherewatch_frontend.domain.model.Movie
import com.example.wherewatch_frontend.domain.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for searching movies by title and managing the list of results.
 *
 * Holds state flows for the search results, loading status, error messages, and the selected movie.
 *
 * @param repository The repository used to fetch movies data.
 */
class MovieSelectionViewModel(
    private val repository: MovieRepository
): ViewModel() {
    /** List of movies returned from the search query. */
    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults: StateFlow<List<Movie>> = _searchResults

    /** Indicates whether a search operation is currently in progress. */
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    /** Holds an error message if a search operation fails, or null otherwise. */
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    /** Currently selected movie from the list. */
    private val _movie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> = _movie

    /**
     * Searches movies by the given [title] asynchronously.
     *
     * Updates loading status and error message accordingly.
     *
     * @param title The title string used for searching movies.
     */
    fun searchMoviesByTitle(title: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val movies = repository.searchMoviesByTitle(title)
                _searchResults.value = movies
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error desconocido"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Sets the currently selected [movie].
     *
     * @param movie The movie selected by the user.
     */
    fun selectMovie(movie: Movie) {
        _movie.value = movie
    }


}