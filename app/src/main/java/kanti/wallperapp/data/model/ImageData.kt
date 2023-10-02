package kanti.wallperapp.data.model

data class ImageData(
	val title: String,
	val link: String,
	var favourite: Boolean = false,
	val position: Int = 0
)
