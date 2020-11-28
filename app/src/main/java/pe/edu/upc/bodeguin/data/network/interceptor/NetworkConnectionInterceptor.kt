package pe.edu.upc.bodeguin.data.network.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import pe.edu.upc.bodeguin.R
import pe.edu.upc.bodeguin.util.NoInternetException
import java.lang.Exception

class NetworkConnectionInterceptor(
    context: Context
): Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isInternetAvailable())
            throw NoInternetException(applicationContext.resources.getString(R.string.not_internet))
        try {
            val chainResponse = chain.proceed(chain.request())
            if (chainResponse.code() == 403) {
                throw NoInternetException(applicationContext.resources.getString(R.string.no_connection_services))
            } else {
                return chainResponse
            }
        } catch (e: Exception){
            throw NoInternetException(applicationContext.resources.getString(R.string.no_connection_services))
        }
    }

    private fun isInternetAvailable() : Boolean {
        var result = false
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actualNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actualNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actualNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actualNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when(type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }
        return result
    }
}