package kanti.wallperapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomTag::class, RoomImageData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

	 abstract fun favoriteTagDao(): TagDao

	 abstract fun favouriteImageDataDao(): ImageDataDao

}