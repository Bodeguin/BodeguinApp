package pe.edu.upc.bodeguin.data.repository

import pe.edu.upc.bodeguin.data.network.api.SafeApiRequest
import pe.edu.upc.bodeguin.data.network.model.response.CategoryResponse
import pe.edu.upc.bodeguin.data.network.service.AppService

class CategoryRepository(
    private val api: AppService
) : SafeApiRequest() {
    suspend fun getCategoriesApi(token: String): CategoryResponse {
        return apiRequest { api.getCategories(token) }
    }
}