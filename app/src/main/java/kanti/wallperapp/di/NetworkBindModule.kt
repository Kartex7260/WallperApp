package kanti.wallperapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kanti.wallperapp.data.ImageRemoteDataSource
import kanti.wallperapp.data.ImageRetrofitDataSource
import kanti.wallperapp.data.TagsRemoteDataSource
import kanti.wallperapp.data.TagsRetrofitDataSource

@Module
@InstallIn(ActivityComponent::class)
abstract class NetworkBindModule {

	@Binds
	abstract fun bindImageRemoteDataSource(imageRemote: ImageRetrofitDataSource): ImageRemoteDataSource

	@Binds
	abstract fun bindTagsRemoteDataSource(tagsRemote: TagsRetrofitDataSource): TagsRemoteDataSource

}