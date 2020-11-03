package pe.edu.upc.bodeguin.data.network.service

import pe.edu.upc.bodeguin.data.network.model.request.LoginRequest
import pe.edu.upc.bodeguin.data.network.model.request.SignUpRequest
import pe.edu.upc.bodeguin.data.network.model.request.UpdateRequest
import pe.edu.upc.bodeguin.data.network.model.request.VoucherRequest
import pe.edu.upc.bodeguin.data.network.model.response.*
import retrofit2.Response
import retrofit2.http.*

interface AppService {
    @POST("api/login")
    suspend fun authenticate(@Body loginRequest: LoginRequest): Response<LoginResponse>
    @POST("api/login/register")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>
    @PUT("api/users/{id}")
    suspend fun updateUserApi(@Header("Authorization") token: String,  @Path("id") id: Int, @Body updateRequest: UpdateRequest): Response<UserResponse>
    @GET("api/users/{id}")
    suspend fun getUser(@Header("Authorization") token: String, @Path("id") id: Int): Response<UserResponse>
    @GET("api/stores")
    suspend fun getStores(@Header("Authorization") token: String): Response<StoreResponse>
    @GET("api/products")
    suspend fun getProducts(@Header("Authorization") token: String, @Query("search") query: String): Response<ProductResponse>
    @GET("api/categories")
    suspend fun getCategories(@Header("Authorization") token: String): Response<CategoryResponse>
    @GET("api/categories/{id}/products")
    suspend fun getProductsByCategory(@Header("Authorization") token: String, @Path("id") id: Int): Response<ProductResponse>
    @GET("api/products/{id}/stores")
    suspend fun getStoresByProduct(@Header("Authorization") token: String, @Path("id") id: Int): Response<ProductStoreResponse>
    @POST("api/shops")
    suspend fun buyShopCart(@Header("Authorization") token: String, @Body voucherRequest: VoucherRequest): Response<ShopCartResponse>
}