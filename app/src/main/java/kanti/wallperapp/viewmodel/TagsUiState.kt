package kanti.wallperapp.viewmodel

import kanti.wallperapp.data.repositories.RepositoryResult
import kanti.wallperapp.data.model.Tag

data class TagsUiState(
	val tags: RepositoryResult<List<Tag>>
)