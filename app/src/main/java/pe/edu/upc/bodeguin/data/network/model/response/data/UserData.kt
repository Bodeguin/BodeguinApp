package pe.edu.upc.bodeguin.data.network.model.response.data

class UserData (
    var id: Int,
    var name: String,
    var firstLastName: String,
    var secondLastName: String,
    var email: String,
    var password: String,
    var direction: String,
    var dni: String,
    var token: String?
)