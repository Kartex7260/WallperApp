package kanti.wallperapp.data.retrofit.model

data class MetaData<T>(
	val isSuccessful: Boolean,
	val status: Int,
	val data: T? = null
)