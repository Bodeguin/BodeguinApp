package pe.edu.upc.bodeguin.data.network.model.response.data

class ProductStoreData (
    var id: Int,
    var quantity: Int,
    var price: String,
    var measureUnit: String,
    var store: String,
    var urlImageProduct: String,
    var product: String,
    var latitude: Double,
    var longitude: Double
)