package kanti.wallperapp.data.repositories

import kanti.wallperapp.data.datasource.FavoriteTagsLocalDataSource
import kanti.wallperapp.data.model.Tag
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteTagsRepository @Inject constructor(
	private val favouriteTagsLocal: FavoriteTagsLocalDataSource
) {

	suspend fun getAll() = favouriteTagsLocal.getAll()

	suspend fun onFavourite(tag: Tag, isFavourite: Boolean): Tag {
		if (isFavourite) {
			favouriteTagsLocal.insert(tag)
		} else {
			favouriteTagsLocal.delete(tag)
		}
		return favouriteTagsLocal.get(tag)
	}

	suspend fun syncFavourite(tag: Tag): Tag? {
		val isFavourite = isFavourite(tag)
		if (isFavourite == tag.favourite)
			return null
		return favouriteTagsLocal.get(tag)
	}

	suspend fun isFavourite(tag: Tag) = favouriteTagsLocal.isFavourite(tag)

}