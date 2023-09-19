package kanti.wallperapp.data.retrofit

data class TagItemsDTO(
	val items: List<TagItemDTO>
)

data class TagItemDTO(
	val images: List<ItemImageDTO>
)

data class ItemImageDTO(
	val id: String,
	val link: String
)