package pe.edu.upc.bodeguin.data.repository

import pe.edu.upc.bodeguin.data.network.api.ApiGateway
import pe.edu.upc.bodeguin.data.network.api.SafeApiRequest
import pe.edu.upc.bodeguin.data.network.interceptor.NetworkConnectionInterceptor
import pe.edu.upc.bodeguin.data.network.model.response.StoreResponse
import pe.edu.upc.bodeguin.data.persistance.database.AppDatabase

class StoreRepository(
    private val networkConnectionInterceptor: NetworkConnectionInterceptor,
    private val api: ApiGateway,
    private val db: AppDatabase
) : SafeApiRequest() {
    suspend fun getStoresApi(token: String) : StoreResponse {
        return apiRequest { api.instance(networkConnectionInterceptor).getStores(token) }
    }
}