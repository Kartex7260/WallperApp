package kanti.wallperapp.data.datasource

import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.data.model.toTag
import kanti.wallperapp.data.room.FavoriteTagDao
import javax.inject.Inject

class FavoriteTagsRoomDataSource @Inject constructor(
	private val dao: FavoriteTagDao
) : FavoriteTagsLocalDataSource {

	override suspend fun getAll(): List<Tag> = dao.getAll().map { it.toTag() }

	override suspend fun add(tag: Tag) = dao.insert(tag)

	override suspend fun delete(tag: Tag) = dao.delete(tag)

}