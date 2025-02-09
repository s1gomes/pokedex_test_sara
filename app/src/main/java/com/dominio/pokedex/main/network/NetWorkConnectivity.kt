package com.dominio.pokedex.main.network

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.ConnectivityManagerCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn

private data class NetworkState(
    /** Determines if the network is connected. */
    val isConnected: Boolean,

    /** Determines if the network is validated - has a working Internet connection. */
    val isValidated: Boolean,

    /** Determines if the network is metered. */
    val isMetered: Boolean,

    /** Determines if the network is not roaming. */
    val isNotRoaming: Boolean
)

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}
private val ConnectivityManager.isActiveNetworkValidated: Boolean
    @SuppressLint("RestrictedApi", "MissingPermission")
    get() =
        try {
            val network = activeNetwork
            val capabilities = getNetworkCapabilities(network)
            (capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) ?: false
        } catch (exception: SecurityException) {
            Log.i("NetworkStateTracker",  "Unable to validate active network")
            false
        }

@Suppress("DEPRECATION")
private val ConnectivityManager.activeNetworkState: NetworkState
    @SuppressLint("MissingPermission")
    get() {
        // Use getActiveNetworkInfo() instead of getNetworkInfo(network) because it can detect VPNs.
        val info = activeNetworkInfo
        val isConnected = info != null && info.isConnected
        val isValidated = isActiveNetworkValidated
        val isMetered = ConnectivityManagerCompat.isActiveNetworkMetered(this)
        val isNotRoaming = info != null && !info.isRoaming
        return NetworkState(isConnected, isValidated, isMetered, isNotRoaming)
    }


@SuppressLint("MissingPermission")
@ExperimentalCoroutinesApi
private fun Context.observeConnectivityAsFlow() =
    callbackFlow {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        fun sendConnectionState() =
            with(connectivityManager.activeNetworkState) {
                if (Build.VERSION.SDK_INT < 23) {
                    if (isConnected) {
                        trySend(ConnectionState.Available)
                    } else {
                        trySend(ConnectionState.Unavailable)
                    }
                } else {
                    if (isConnected && isValidated) {
                        trySend(ConnectionState.Available)
                    } else {
                        trySend(ConnectionState.Unavailable)
                    }
                }
            }

        val networkRequest =
            NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build()

        val networkCallback =
            object : ConnectivityManager.NetworkCallback() {
                // satisfies network capability and transport requirements requested in networkRequest
                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    sendConnectionState()
                }
                override fun onLost(network: Network) {
                    sendConnectionState()
                }
            }

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)



@ExperimentalCoroutinesApi
@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current
    return produceState<ConnectionState>(initialValue = ConnectionState.Unavailable) {
        context.observeConnectivityAsFlow().collect { value = it }
    }
}


