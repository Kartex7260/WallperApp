package kanti.wallperapp.net

import android.content.Context
import android.content.res.Resources
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import coil.request.ImageRequest
import coil.size.Precision
import coil.size.Scale
import kanti.wallperapp.R
import kanti.wallperapp.data.model.ImageLink

fun Context.imageRequestBuilder(
	lifecycleOwner: LifecycleOwner,
	imageLink: ImageLink,
	imageView: ImageView
): ImageRequest.Builder {
	return ImageRequest.Builder(this)
		.data(imageLink.link)
		.lifecycle(lifecycleOwner)
		.diskCacheKey(imageLink.link)
		.memoryCacheKey(imageLink.link)
		.target(imageView)
		.fallback(R.drawable.outline_image_not_supported_24)
		.placeholder(R.drawable.outline_image_24)
}

fun Context.smallImageRequest(
	lifecycleOwner: LifecycleOwner,
	imageLink: ImageLink,
	imageView: ImageView
): ImageRequest {
	return imageRequestBuilder(lifecycleOwner, imageLink, imageView)
		.allowRgb565(true)
		.precision(Precision.INEXACT)
		.scale(Scale.FILL)
		.build()
}

fun Context.largeImageRequest(
	lifecycleOwner: LifecycleOwner,
	imageLink: ImageLink,
	imageView: ImageView
): ImageRequest {
	return imageRequestBuilder(lifecycleOwner, imageLink, imageView)
		.scale(Scale.FIT)
		.build()
}