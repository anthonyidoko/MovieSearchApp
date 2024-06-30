package com.example.moviesearch.utils.connectivity

import kotlinx.coroutines.flow.Flow

interface NetworkConnectionObserver {
    fun observe(): Flow<NetworkStatus>
}