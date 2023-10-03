package kanti.wallperapp.data.datasource

import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.data.model.asTag
import kanti.wallperapp.data.room.TagDao
import javax.inject.Inject

class FavoriteTagsRoomDataSource @Inject constructor(
	private val dao: TagDao
) : FavoriteTagsLocalDataSource {

	override suspend fun getAll() = dao.getAll().map { it.asTag() }.toMutableList()

	override suspend fun get(tag: Tag): Tag {
		return dao.get(tag)?.asTag() ?: return tag.copy(favourite = false)
	}

	override suspend fun insert(tag: Tag) = dao.insert(tag)

	override suspend fun delete(tag: Tag) = dao.delete(tag)

	override suspend fun isFavourite(tag: Tag) = dao.isFavourite(tag)

}