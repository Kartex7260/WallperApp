package kanti.wallperapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteTag::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

	 abstract fun favoriteTagDao(): FavoriteTagDao

}