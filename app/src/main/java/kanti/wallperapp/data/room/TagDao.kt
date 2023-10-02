package kanti.wallperapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.data.model.asRoomTag

@Dao
abstract class TagDao {

	@Query("SELECT EXISTS(SELECT 1 FROM favourite_tags WHERE name = :name)")
	abstract suspend fun isFavourite(name: String): Boolean

	suspend fun isFavourite(tag: Tag) = isFavourite(tag.name)

	@Query("SELECT * FROM favourite_tags ORDER BY position")
	abstract suspend fun getAll(): List<RoomTag>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	abstract suspend fun insert(tag: RoomTag)

	suspend fun insert(tag: Tag) {
		val favoriteTag = tag.asRoomTag()
		insert(favoriteTag)
	}

	@Delete
	abstract suspend fun delete(tag: RoomTag)

	suspend fun delete(tag: Tag) {
		val favoriteTag = tag.asRoomTag()
		delete(favoriteTag)
	}

}