package kanti.wallperapp.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_tags")
data class FavouriteTag(
	@PrimaryKey val name: String,
	@ColumnInfo(name = "display_name") val displayName: String,
	val position: Int
)
