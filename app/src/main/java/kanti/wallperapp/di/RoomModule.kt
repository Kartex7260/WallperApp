package kanti.wallperapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kanti.wallperapp.R
import kanti.wallperapp.data.room.AppDatabase
import kanti.wallperapp.data.room.ImageDataDao
import kanti.wallperapp.data.room.TagDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

	@Provides
	fun provideTagDao(room: AppDatabase): TagDao {
		return room.favoriteTagDao()
	}

	@Provides
	fun provideImageDataDao(room: AppDatabase): ImageDataDao {
		return room.favouriteImageDataDao()
	}

	@Singleton
	@Provides
	fun provideRoom(@ApplicationContext context: Context): AppDatabase {
		return Room.databaseBuilder(
			context,
			AppDatabase::class.java,
			context.getString(R.string.database_name)
		).build()
	}

}