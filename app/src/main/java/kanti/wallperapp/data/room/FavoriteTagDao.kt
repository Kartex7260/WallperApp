package kanti.wallperapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.data.model.toFavoriteTag

@Dao
abstract class FavoriteTagDao {

	@Query("SELECT * FROM favorite_tags")
	abstract suspend fun getAll(): List<FavoriteTag>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	abstract suspend fun insert(tag: FavoriteTag)

	suspend fun insert(tag: Tag) {
		val favoriteTag = tag.toFavoriteTag()
		insert(favoriteTag)
	}

	@Delete
	abstract suspend fun delete(tag: FavoriteTag)

	suspend fun delete(tag: Tag) {
		val favoriteTag = tag.toFavoriteTag()
		delete(favoriteTag)
	}

}