package kanti.wallperapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kanti.wallperapp.data.datasource.FavoriteTagsLocalDataSource
import kanti.wallperapp.data.datasource.FavoriteTagsRoomDataSource

@Module
@InstallIn(SingletonComponent::class)
abstract class RoomBindModule {

	@Binds
	abstract fun bindFavoriteTagRoomDataSource(dataSource: FavoriteTagsRoomDataSource): FavoriteTagsLocalDataSource

}
