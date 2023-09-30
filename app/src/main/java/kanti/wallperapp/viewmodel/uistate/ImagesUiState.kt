package kanti.wallperapp.viewmodel.uistate

import kanti.wallperapp.data.model.ImageData
import kanti.wallperapp.data.repositories.RepositoryResult

data class ImagesUiState(
	val images: RepositoryResult<List<ImageData>>? = null,
	val process: Boolean = false
)
