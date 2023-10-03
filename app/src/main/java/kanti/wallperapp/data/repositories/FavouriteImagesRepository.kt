package kanti.wallperapp.data.repositories

import kanti.wallperapp.data.datasource.FavouriteImagesLocalDataSource
import kanti.wallperapp.data.model.ImageData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteImagesRepository @Inject constructor(
	private val favouriteImagesLocal: FavouriteImagesLocalDataSource
) {

	suspend fun getAll() = favouriteImagesLocal.getAll()

	suspend fun onFavourite(image: ImageData, isFavourite: Boolean): ImageData {
		if (isFavourite) {
			favouriteImagesLocal.insert(image)
		} else {
			favouriteImagesLocal.delete(image)
		}
		return favouriteImagesLocal.get(image)
	}

	suspend fun syncFavourite(image: ImageData): ImageData? {
		val isFavourite = isFavourite(image)
		if (isFavourite == image.favourite)
			return null
		return favouriteImagesLocal.get(image)
	}

	suspend fun isFavourite(image: ImageData) = favouriteImagesLocal.isFavourite(image)

}