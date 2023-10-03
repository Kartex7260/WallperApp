package kanti.wallperapp.data.datasource

import kanti.wallperapp.data.model.ImageData

interface FavouriteImagesLocalDataSource {

	suspend fun getAll(): MutableList<ImageData>

	suspend fun get(imageData: ImageData): ImageData

	suspend fun insert(image: ImageData)

	suspend fun delete(image: ImageData)

	suspend fun isFavourite(image: ImageData): Boolean

}