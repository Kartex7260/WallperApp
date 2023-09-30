package kanti.wallperapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.data.model.toFavoriteTag

@Dao
abstract class FavouriteTagDao {

	@Query("SELECT EXISTS(SELECT 1 FROM favorite_tags WHERE name = :name)")
	abstract suspend fun isFavourite(name: String): Boolean

	@Query("SELECT * FROM favorite_tags ORDER BY position")
	abstract suspend fun getAll(): List<FavouriteTag>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	abstract suspend fun insert(tag: FavouriteTag)

	suspend fun insert(tag: Tag) {
		val favoriteTag = tag.toFavoriteTag()
		insert(favoriteTag)
	}

	@Delete
	abstract suspend fun delete(tag: FavouriteTag)

	suspend fun delete(tag: Tag) {
		val favoriteTag = tag.toFavoriteTag()
		delete(favoriteTag)
	}

}