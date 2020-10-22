package pe.edu.upc.bodeguin.ui.view.home.profile

import pe.edu.upc.bodeguin.data.persistance.model.User

interface UserListener {
    fun onSuccess()
    fun onSuccessUpdate(user: User)
    fun onFailure(message: String)
    fun onClose()
}