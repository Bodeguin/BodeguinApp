package pe.edu.upc.bodeguin.data.network.api

import org.json.JSONException
import org.json.JSONObject
import pe.edu.upc.bodeguin.util.ApiException
import retrofit2.Response

abstract class SafeApiRequest {
    suspend fun<T: Any> apiRequest(call: suspend () -> Response<T>) : T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody()?.string()
            val message = StringBuilder()
            error?.let {
                try {
                    message.append(JSONObject(it).getString("message"))
                } catch (e: JSONException) { }
                message.append("")
            }
            val errorCode: String = if (response.code() == 401)
                "Unauthorized"
            else
                response.code().toString()
            message.append("Error Code: $errorCode")
            throw ApiException(message.toString())
        }
    }
}