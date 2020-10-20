package pe.edu.upc.bodeguin.ui.view.authentication

import pe.edu.upc.bodeguin.data.persistance.model.User

interface RegisterListener {
    fun start()
    fun success(user: User)
    fun fail(message: String)
}