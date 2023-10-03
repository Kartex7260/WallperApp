package kanti.wallperapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kanti.wallperapp.data.model.ImageData
import kanti.wallperapp.data.model.asRoomImageData

@Dao
abstract class ImageDataDao {

	@Query("SELECT EXISTS(SELECT 1 FROM favourite_images WHERE link = :link)")
	abstract suspend fun isFavourite(link: String): Boolean

	suspend fun isFavourite(imageData: ImageData): Boolean = isFavourite(imageData.link)

	@Query("SELECT * FROM favourite_images ORDER BY position")
	abstract suspend fun getAll(): List<RoomImageData>

	@Query("SELECT * FROM favourite_images WHERE link = :link")
	abstract suspend fun get(link: String): List<RoomImageData>

	suspend fun get(imageData: ImageData): RoomImageData? {
		val roomImages = get(imageData.link)
		if (roomImages.isEmpty())
			return null
		return roomImages[0]
	}

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	abstract suspend fun insert(imageData: RoomImageData)

	suspend fun insert(imageData: ImageData) {
		val roomImageData = imageData.asRoomImageData()
		insert(roomImageData)
	}

	@Delete
	abstract suspend fun delete(imageData: RoomImageData)

	suspend fun delete(imageData: ImageData) {
		val roomImageData = imageData.asRoomImageData()
		delete(roomImageData)
	}

}