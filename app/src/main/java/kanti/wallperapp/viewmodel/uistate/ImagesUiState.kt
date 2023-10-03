package kanti.wallperapp.viewmodel.uistate

import kanti.wallperapp.data.model.ImageData
import kanti.wallperapp.data.repositories.RepositoryResult

data class ImagesUiState(
	val images: RepositoryResult<MutableList<ImageData>>? = null,
	val process: Boolean = false
)
