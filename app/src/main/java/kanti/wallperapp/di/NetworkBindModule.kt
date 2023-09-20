package kanti.wallperapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kanti.wallperapp.data.datasource.ImageRemoteDataSource
import kanti.wallperapp.data.datasource.ImageRetrofitDataSource
import kanti.wallperapp.data.datasource.TagsRemoteDataSource
import kanti.wallperapp.data.datasource.TagsRetrofitDataSource

@Module
@InstallIn(ActivityComponent::class)
abstract class NetworkBindModule {

	@Binds
	abstract fun bindImageRemoteDataSource(imageRemote: ImageRetrofitDataSource): ImageRemoteDataSource

	@Binds
	abstract fun bindTagsRemoteDataSource(tagsRemote: TagsRetrofitDataSource): TagsRemoteDataSource

}