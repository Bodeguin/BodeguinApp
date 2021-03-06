package pe.edu.upc.bodeguin.util

import android.content.Context
import android.view.View
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.snackbar.Snackbar

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.snackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}

fun LottieAnimationView.show() {
    visibility = View.VISIBLE
}

fun LottieAnimationView.hide() {
    visibility = View.GONE
}
