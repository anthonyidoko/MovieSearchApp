package com.example.moviesearch.utils
sealed class NetworkResource<T>(data: T? = null, message: String? = null) {
    data class Success<T>(val data: T?): NetworkResource<T>(data = data)
    class Error<T>(val message: String?): NetworkResource<T>(message = message)
}