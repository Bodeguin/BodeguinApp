package pe.edu.upc.bodeguin.data.network.model.response

import pe.edu.upc.bodeguin.data.network.model.response.data.ProductStoreData

class ProductStoreResponse (
    var valid: Boolean,
    var message: String,
    var data: List<ProductStoreData>,
    var errorCode: Int
)