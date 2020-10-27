package pe.edu.upc.bodeguin.data.network.model.response

import pe.edu.upc.bodeguin.data.network.model.response.data.StoreData

class StoreResponse (
    var valid: Boolean,
    var message: String,
    var data: List<StoreData>,
    var errorCode: Int
)