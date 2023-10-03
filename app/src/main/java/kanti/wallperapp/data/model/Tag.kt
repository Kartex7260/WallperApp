package kanti.wallperapp.data.model

data class Tag(
	val name: String,
	val displayName: String,
	val favourite: Boolean = false,
	val position: Int = 0
)
