package pe.edu.upc.bodeguin.util

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("image")
fun loadImage(view: ImageView, url: String?) {
    Glide
        .with(view.context)
        .load(url)
        .into(view)
}