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

class MovieDetailsViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val samplePlatform = Platform(1L, "Netflix", "https://example.com/netflix.png")
    private val sampleCountry = Country(1L, "España", "ES")
    private val sampleAvailability = Availability(platform = samplePlatform, country = sampleCountry)

    private val sampleMovie = Movie(
        id = 1L,
        title = "Matrix",
        overview = "Una película de ciencia ficción sobre realidades simuladas.",
        releaseDate = "1999-03-31",
        availabilities = listOf(sampleAvailability)
    )

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> = _movie


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // Estados para filtros
    private val _selectedPlatforms = MutableStateFlow<Set<Platform>>(emptySet())
    val selectedPlatforms: StateFlow<Set<Platform>> = _selectedPlatforms

    private val _selectedCountries = MutableStateFlow<Set<Country>>(emptySet())
    val selectedCountries: StateFlow<Set<Country>> = _selectedCountries

    // Availabilities filtradas
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

    fun loadMovieByTitle(title: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val movies = repository.searchMoviesByTitle(title)
                _movie.value = movies.firstOrNull()
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error desconocido"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Métodos para manejar filtros
    fun togglePlatform(platform: Platform) {
        _selectedPlatforms.value = _selectedPlatforms.value.toMutableSet().apply {
            if (!add(platform)) remove(platform)
        }
    }

    fun toggleCountry(country: Country) {
        _selectedCountries.value = _selectedCountries.value.toMutableSet().apply {
            if (!add(country)) remove(country)
        }
    }

    fun clearFilters() {
        _selectedPlatforms.value = emptySet()
        _selectedCountries.value = emptySet()
    }

}
