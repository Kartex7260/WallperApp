package kanti.wallperapp.viewmodel.uistate

import kanti.wallperapp.data.model.Tag

data class FavouriteTagsUiState(
	val tags: List<Tag>? = null,
	val process: Boolean = false
)