package kanti.wallperapp.di

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoilModule {

	@Provides
	@Singleton
	fun provideImageLoader(@ApplicationContext context: Context): ImageLoader {
		return ImageLoader.Builder(context)
			.diskCachePolicy(CachePolicy.ENABLED)
			.diskCache {
				DiskCache.Builder()
					.directory(context.cacheDir.resolve("image_cache"))
					.maxSizePercent(0.1)
					.build()
			}
			.memoryCachePolicy(CachePolicy.ENABLED)
			.memoryCache {
				MemoryCache.Builder(context)
					.maxSizePercent(0.7)
					.build()
			}
			.build()
	}

}