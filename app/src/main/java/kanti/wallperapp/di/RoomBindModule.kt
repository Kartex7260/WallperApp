package kanti.wallperapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kanti.wallperapp.data.datasource.FavoriteTagsLocalDataSource
import kanti.wallperapp.data.datasource.FavoriteTagsRoomDataSource
import kanti.wallperapp.data.datasource.FavouriteImagesLocalDataSource
import kanti.wallperapp.data.datasource.FavouriteImagesRoomDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RoomBindModule {

	@Singleton
	@Binds
	abstract fun bindFavoriteTagsRoomDataSource(dataSource: FavoriteTagsRoomDataSource): FavoriteTagsLocalDataSource

	@Singleton
	@Binds
	abstract fun bindFavouriteImagesRoomDataSource(dataSource: FavouriteImagesRoomDataSource): FavouriteImagesLocalDataSource

}
