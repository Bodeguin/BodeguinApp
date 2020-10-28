package pe.edu.upc.bodeguin.data.network.model.response

import pe.edu.upc.bodeguin.data.network.model.response.data.CategoryData

class CategoryResponse (
    var valid: Boolean,
    var message: String,
    var data: List<CategoryData>,
    var errorCode: Int
)