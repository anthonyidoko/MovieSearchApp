package com.example.moviesearch.utils.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkConnectionObserverImpl @Inject constructor(
    @ApplicationContext private val context: Context
): NetworkConnectionObserver {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    override fun observe(): Flow<NetworkStatus> {
        return callbackFlow {
            val callback = object: ConnectivityManager.NetworkCallback(){
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch {send(NetworkStatus.Available)}
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch {send(NetworkStatus.Lost)}

                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch {send(NetworkStatus.UnAvailable)}
                }
            }

            connectivityManager.registerDefaultNetworkCallback(callback)
            awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
        }.distinctUntilChanged()
    }
}