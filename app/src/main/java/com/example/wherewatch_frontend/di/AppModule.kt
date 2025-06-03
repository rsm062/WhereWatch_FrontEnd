package com.example.wherewatch_frontend.di


import com.example.wherewatch_frontend.presentation.viewmodel.MovieDetailsViewModel
import com.example.wherewatch_frontend.data.repository.MovieRestRepository
import com.example.wherewatch_frontend.domain.repository.MovieRepository
import com.example.wherewatch_frontend.presentation.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single<MovieRepository> { MovieRestRepository(get()) }


    viewModel { MovieDetailsViewModel(get()) }
    viewModel { SearchViewModel() }

}