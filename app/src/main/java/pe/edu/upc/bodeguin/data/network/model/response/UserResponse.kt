package pe.edu.upc.bodeguin.data.network.model.response

import pe.edu.upc.bodeguin.data.network.model.response.data.UserData

class UserResponse (
    var valid: Boolean,
    var message: String,
    var data: UserData,
    var errorCode: Int
)