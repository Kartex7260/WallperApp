package kanti.wallperapp.data.retrofit

import com.google.gson.annotations.SerializedName

data class TagsDTO(
	val tags: List<TagPOJO>
)

data class TagPOJO(
	val name: String,
	@SerializedName("display_name") val displayName: String
)
