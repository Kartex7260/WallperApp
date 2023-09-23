package kanti.wallperapp.net

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import coil.request.ImageRequest
import coil.size.Precision
import coil.size.Scale
import kanti.wallperapp.R
import kanti.wallperapp.data.model.ImageData

fun Context.imageRequestBuilder(
	lifecycleOwner: LifecycleOwner,
	imageData: ImageData,
	imageView: ImageView
): ImageRequest.Builder {
	return ImageRequest.Builder(this)
		.data(imageData.link)
		.lifecycle(lifecycleOwner)
		.diskCacheKey(imageData.link)
		.memoryCacheKey(imageData.link)
		.target(imageView)
		.fallback(R.drawable.outline_image_not_supported_24)
		.error(R.drawable.outline_image_not_supported_24)
		.placeholder(R.drawable.outline_image_24)
}

fun Context.smallImageRequest(
	lifecycleOwner: LifecycleOwner,
	imageData: ImageData,
	imageView: ImageView
): ImageRequest {
	return imageRequestBuilder(lifecycleOwner, imageData, imageView)
		.allowRgb565(true)
		.precision(Precision.INEXACT)
		.scale(Scale.FILL)
		.build()
}

fun Context.largeImageRequest(
	lifecycleOwner: LifecycleOwner,
	imageData: ImageData,
	imageView: ImageView
): ImageRequest {
	return imageRequestBuilder(lifecycleOwner, imageData, imageView)
		.precision(Precision.EXACT)
		.scale(Scale.FILL)
		.build()
}