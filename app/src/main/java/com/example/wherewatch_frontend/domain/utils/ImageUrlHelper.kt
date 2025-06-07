package com.example.wherewatch_frontend.domain.utils

/**
 * Utility object for building full image URLs from partial paths.
 */
object ImageUrlHelper {
    /**
     * Builds the full URL for a movie poster image from a partial path.
     *
     * @param posterPath The partial image path returned by the API.
     * @param size The desired size (default is "w500").
     * @return A complete URL to the image resource or null if the path is null.
     */
    fun buildPosterUrl(posterPath: String?, size: String = "w500"): String? {
        return posterPath?.let { "https://image.tmdb.org/t/p/$size$it" }
    }

    /**
     * Builds the full URL for a platform logo from a partial path.
     *
     * @param logoPath The partial image path for the logo.
     * @param size The desired size (default is "original").
     * @return A complete URL to the logo image or null if the path is null.
     */
    fun buildLogoUrl(logoPath: String?, size: String = "original"): String? {
        return logoPath?.let { "https://image.tmdb.org/t/p/$size$it" }
    }
}