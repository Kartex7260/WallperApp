package kanti.wallperapp.data.datasource

import kanti.wallperapp.data.model.ImageData

interface FavouriteImagesLocalDataSource {

	suspend fun getAll(): List<ImageData>

	suspend fun add(image: ImageData)

	suspend fun delete(image: ImageData)

	suspend fun isFavourite(image: ImageData): Boolean

}