package com.example.wherewatch_frontend.di


import com.example.wherewatch_frontend.presentation.viewmodel.MovieDetailsViewModel
import com.example.wherewatch_frontend.data.repository.MovieRestRepository
import com.example.wherewatch_frontend.domain.repository.MovieRepository
import com.example.wherewatch_frontend.presentation.viewmodel.MovieSelectionViewModel
import com.example.wherewatch_frontend.presentation.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module that provides dependency injection setup for the application layer.
 *
 * This module includes:
 * - A singleton instance of MovieRepository implemented by MovieRestRepository.
 * - ViewModel instances for:
 *   - MovieDetailsViewModel
 *   - SearchViewModel
 *   - MovieSelectionViewModel
 *
 * These ViewModels are injected wherever needed in the presentation layer.
 */
val appModule = module {
    /**
     * Provides a singleton instance of MovieRepository using MovieRestRepository.
     * The repository requires a dependency that is injected via `get()`.
     */
    single<MovieRepository> { MovieRestRepository(get()) }

    /**
     * Provides an instances of ViewModels
     */
    viewModel { MovieDetailsViewModel(get()) }
    viewModel { SearchViewModel() }
    viewModel { MovieSelectionViewModel(get()) }

}