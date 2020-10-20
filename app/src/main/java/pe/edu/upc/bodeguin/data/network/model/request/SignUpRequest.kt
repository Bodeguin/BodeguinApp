package pe.edu.upc.bodeguin.data.network.model.request

class SignUpRequest (
    var id: Int? = 0,
    var correo: String? = "",
    var password: String? = "",
    var nombre:String? = "",
    var apellidoPaterno:String? = "",
    var apellidoMaterno:String? = "",
    var direccion: String? = "",
    var dni: String? = "",
    var enable: Boolean? = true,
    var adm: Boolean? = false
)