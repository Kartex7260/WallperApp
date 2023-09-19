package kanti.wallperapp.data.retrofit

data class DataResponse<T>(
	val isSuccessful: Boolean,
	val status: Int,
	val data: T? = null
) {
	var message: String = ""
}