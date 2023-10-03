package kanti.wallperapp.viewmodel.uistate

import kanti.wallperapp.data.model.Tag

data class FavouriteTagsUiState(
	val tags: MutableList<Tag>? = null,
	val process: Boolean = false
)