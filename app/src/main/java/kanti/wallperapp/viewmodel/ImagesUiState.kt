package kanti.wallperapp.viewmodel

import kanti.wallperapp.data.model.ImageData
import kanti.wallperapp.data.repositories.RepositoryResult

data class ImagesUiState(
	val images: RepositoryResult<List<ImageData>>
)
