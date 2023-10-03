package kanti.wallperapp.viewmodel.uistate

import kanti.wallperapp.data.model.ImageData

data class FavouriteImagesUiState(
	val images: MutableList<ImageData>? = null,
	val process: Boolean = false
)
