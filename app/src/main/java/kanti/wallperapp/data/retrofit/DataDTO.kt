package kanti.wallperapp.data.retrofit

data class DataDTO<T>(
	val success: Boolean,
	val status: Int,
	val data: T?
)