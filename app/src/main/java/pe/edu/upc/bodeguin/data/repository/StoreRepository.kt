package pe.edu.upc.bodeguin.data.repository

import pe.edu.upc.bodeguin.data.network.api.SafeApiRequest
import pe.edu.upc.bodeguin.data.network.model.response.StoreResponse
import pe.edu.upc.bodeguin.data.network.service.AppService

class StoreRepository(
    private val api: AppService
) : SafeApiRequest() {
    suspend fun getStoresApi(token: String) : StoreResponse {
        return apiRequest { api.getStores(token) }
    }
}