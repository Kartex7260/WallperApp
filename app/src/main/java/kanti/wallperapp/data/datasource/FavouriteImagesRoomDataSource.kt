package kanti.wallperapp.data.datasource

import kanti.wallperapp.data.model.ImageData
import kanti.wallperapp.data.model.asImageData
import kanti.wallperapp.data.room.ImageDataDao
import javax.inject.Inject

class FavouriteImagesRoomDataSource @Inject constructor(
	private val favouriteImages: ImageDataDao
) : FavouriteImagesLocalDataSource {

	override suspend fun getAll() = favouriteImages.getAll().map {
			it.asImageData()
		}

	override suspend fun add(image: ImageData) = favouriteImages.insert(image)

	override suspend fun delete(image: ImageData) = favouriteImages.delete(image)

	override suspend fun isFavourite(image: ImageData) = favouriteImages.isFavourite(image)

}