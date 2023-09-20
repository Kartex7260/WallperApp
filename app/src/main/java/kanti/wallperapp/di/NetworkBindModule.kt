package kanti.wallperapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kanti.wallperapp.data.datasource.ImageRemoteDataSource
import kanti.wallperapp.data.datasource.ImageRetrofitDataSource
import kanti.wallperapp.data.datasource.TagsRemoteDataSource
import kanti.wallperapp.data.datasource.TagsRetrofitDataSource

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkBindModule {

	@Binds
	abstract fun bindImageRemoteDataSource(imageRemote: ImageRetrofitDataSource): ImageRemoteDataSource

	@Binds
	abstract fun bindTagsRemoteDataSource(tagsRemote: TagsRetrofitDataSource): TagsRemoteDataSource

}