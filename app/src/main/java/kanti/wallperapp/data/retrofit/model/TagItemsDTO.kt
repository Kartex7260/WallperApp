package kanti.wallperapp.data.retrofit.model

data class TagItemsDTO(
	val items: List<TagItemDTO>
)

data class TagItemDTO(
	val title: String,
	val images: List<ItemImageDTO>?
)

data class ItemImageDTO(
	val id: String,
	val link: String
)