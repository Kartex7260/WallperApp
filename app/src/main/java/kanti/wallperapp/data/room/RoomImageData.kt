package kanti.wallperapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_images")
data class RoomImageData(
	@PrimaryKey val link: String,
	val title: String,
	val position: Int
)