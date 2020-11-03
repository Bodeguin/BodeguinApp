package pe.edu.upc.bodeguin.data.network.model.response


class ShopCartResponse (
    var valid: Boolean,
    var message: String,
    var data: String,
    var errorCode: Int
)