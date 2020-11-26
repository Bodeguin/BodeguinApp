package pe.edu.upc.bodeguin.data.repository

import pe.edu.upc.bodeguin.data.network.api.SafeApiRequest
import pe.edu.upc.bodeguin.data.network.model.response.ProductResponse
import pe.edu.upc.bodeguin.data.network.model.response.ProductStoreResponse
import pe.edu.upc.bodeguin.data.network.service.AppService

class ProductRepository(
    private val api: AppService
) : SafeApiRequest() {
    suspend fun getProductApi(token: String, query: String) : ProductResponse {
        return apiRequest { api.getProducts(token, query) }
    }
    suspend fun getProductsByCategoryApi(token: String, id: Int) : ProductResponse {
        return apiRequest { api.getProductsByCategory(token, id) }
    }
    suspend fun getStoresByProducts(token: String, id: Int) : ProductStoreResponse {
        return apiRequest { api.getStoresByProduct(token, id) }
    }
}