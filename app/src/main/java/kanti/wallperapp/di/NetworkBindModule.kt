package kanti.wallperapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kanti.wallperapp.data.datasource.ImageLinksRemoteDataSource
import kanti.wallperapp.data.datasource.ImageLinksRetrofitDataSource
import kanti.wallperapp.data.datasource.TagsRemoteDataSource
import kanti.wallperapp.data.datasource.TagsRetrofitDataSource

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkBindModule {

	@Binds
	abstract fun bindImageRemoteDataSource(imageRemote: ImageLinksRetrofitDataSource): ImageLinksRemoteDataSource

	@Binds
	abstract fun bindTagsRemoteDataSource(tagsRemote: TagsRetrofitDataSource): TagsRemoteDataSource

}