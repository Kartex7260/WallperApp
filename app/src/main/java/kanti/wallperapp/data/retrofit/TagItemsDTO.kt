package kanti.wallperapp.data.retrofit

data class TagItemsDTO(
	val items: List<TagItemPOJO>
)

data class TagItemPOJO(
	val images: List<ItemImagePOJO>
)

data class ItemImagePOJO(
	val id: String,
	val link: String
)