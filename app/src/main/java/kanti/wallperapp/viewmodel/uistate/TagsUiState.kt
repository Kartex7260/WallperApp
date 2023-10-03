package kanti.wallperapp.viewmodel.uistate

import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.data.repositories.RepositoryResult

data class TagsUiState(
	val tags: RepositoryResult<MutableList<Tag>>? = null,
	val process: Boolean = false
)