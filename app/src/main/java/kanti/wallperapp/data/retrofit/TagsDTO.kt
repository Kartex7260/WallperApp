package kanti.wallperapp.data.retrofit

import com.google.gson.annotations.SerializedName

data class TagsDTO(
	val tags: List<TagDTO>
)

data class TagDTO(
	val name: String,
	@SerializedName("display_name") val displayName: String
)
