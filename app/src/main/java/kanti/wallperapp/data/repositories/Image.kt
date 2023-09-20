package kanti.wallperapp.data.repositories

import android.widget.ImageView
import coil.load
import coil.request.Disposable

data class Image(
	val link: String
) {
	fun load(imageView: ImageView): Disposable {
		return imageView.load(link)
	}
}
