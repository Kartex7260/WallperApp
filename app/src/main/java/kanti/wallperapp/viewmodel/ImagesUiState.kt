package kanti.wallperapp.viewmodel

import kanti.wallperapp.data.model.ImageLink
import kanti.wallperapp.data.repositories.RepositoryResult

data class ImagesUiState(
	val images: RepositoryResult<List<ImageLink>>
)
