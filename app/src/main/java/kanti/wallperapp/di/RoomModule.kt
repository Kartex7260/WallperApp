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
import kanti.wallperapp.data.room.FavouriteTagDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

	@Provides
	fun provideFavoriteTagDao(room: AppDatabase): FavouriteTagDao {
		return room.favoriteTagDao()
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