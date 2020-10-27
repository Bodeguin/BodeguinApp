package pe.edu.upc.bodeguin.data.network.model.response

import pe.edu.upc.bodeguin.data.network.model.response.data.ProductData

class ProductResponse (
    var valid: Boolean,
    var message: String,
    var data: List<ProductData>,
    var errorCode: Int
)